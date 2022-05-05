package br.com.letscode.compra.kafka;

import br.com.letscode.compra.dto.CompraRequest;
import br.com.letscode.compra.dto.KafkaDTO;
import br.com.letscode.compra.exceptions.BadRequest;
import br.com.letscode.compra.model.Compra;
import br.com.letscode.compra.model.Produto;
import br.com.letscode.compra.model.ProdutoComprado;
import br.com.letscode.compra.repository.CompraRepository;
import br.com.letscode.compra.service.ProdutoService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ReceiveKafkaMessage {

    private final CompraRepository compraRepository;


    @KafkaListener(topics = "topic-compra", groupId = "grupo-1")
    public void listenTopicCreateCompra(KafkaDTO kafkaDTO) throws BadRequest {
        int count = 0;
        CompraRequest compraRequest = kafkaDTO.getCompraRequest();
        List<Compra> compraList = compraRepository.findByCpf(compraRequest.getCpf());
        Compra compra = compraList.get(compraList.size()-1);
        for (Map.Entry<String, Integer> entry : compraRequest.getProdutos().entrySet()) {
            Produto produto = ProdutoService.getProduct(entry, kafkaDTO.getToken());
            if (produto.getQtde_disponivel() < entry.getValue()) {
                //compraProdutoRepository.deleteAll(compra.getProdutos());
                //compra.setStatus("CANCELADO-ESTOQUE-INSUFICIENTE");
                compra.getProdutos().get(count).setStatusProduto("Estoque insuficiente");
            } else {
                //compra.setStatus("CONCLUIDO");
                compra.getProdutos().get(count).setStatusProduto("Possui estoque suficiente");
                //ProdutoService.updateQuantity(entry, kafkaDTO.getToken());
            }
            count++;
        }
        compraRepository.save(compra);
        if(checkStatus(compra)){
            for (Map.Entry<String, Integer> entry : compraRequest.getProdutos().entrySet()) {
                ProdutoService.updateQuantity(entry, kafkaDTO.getToken());
            }
            compra.setStatus("CONCLUIDO");
        }else{
            compra.setStatus("CANCELADO-ESTOQUE-INSUFICIENTE");
        }
        compraRepository.save(compra);
    }

    private boolean checkStatus(Compra compra){
        int i = compra.getProdutos().size();
        int j = 0;
        for(ProdutoComprado produtoComprado : compra.getProdutos()){
            if(Objects.equals(produtoComprado.getStatusProduto(), "Possui estoque suficiente")){
                j++;
            }
        }
        return i==j;
    }

}