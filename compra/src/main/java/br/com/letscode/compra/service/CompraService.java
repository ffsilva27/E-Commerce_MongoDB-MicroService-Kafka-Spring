package br.com.letscode.compra.service;

import br.com.letscode.compra.dto.CompraRequest;
import br.com.letscode.compra.dto.CompraResponse;
import br.com.letscode.compra.dto.KafkaDTO;
import br.com.letscode.compra.exceptions.BadRequest;
import br.com.letscode.compra.kafka.SendKafkaMessage;
import br.com.letscode.compra.model.*;
import br.com.letscode.compra.repository.CompraRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class CompraService {

    private final CompraRepository compraRepository;
    private final SendKafkaMessage sendKafkaMessage;
    private final ProdutoService produtoService;

    public Page<CompraResponse> listCompra(Compra compra) {
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnorePaths("produtos")
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.EXACT);

        Example example = Example.of(compra, matcher);
        Pageable pageable = PageRequest.of(0,5);
        List<Compra> compraList = compraRepository.findAll(example);
        List<CompraResponse> compraResponses = compraList.stream()
                .map(x -> CompraResponse.convert(x))
                .collect(Collectors.toList());
        Page<CompraResponse> retorno = new PageImpl<CompraResponse>(compraResponses, pageable, compraResponses.size());
        return retorno;
    }

    public boolean validaProduto(CompraRequest compraRequest, String token) throws BadRequest {
        int qtdeItens = compraRequest.getProdutos().size();
        int qtdeComparacao = 0;
        for (Map.Entry<String,Integer> entry : compraRequest.getProdutos().entrySet()){
            Produto produto = ProdutoService.getProduct(entry, token);
            if (produto!=null){
                qtdeComparacao++;
            }
        }
        return qtdeItens == qtdeComparacao;
    }

        public void enviaKafka(KafkaDTO kafkaDTO) throws BadRequest {
            CompraRequest compraRequest = kafkaDTO.getCompraRequest();
            if(validaProduto(compraRequest, kafkaDTO.getToken())){
                double sum_values = 0.0;

                Compra compra = new Compra();
                compra.setId(UUID.randomUUID().toString());
                compra.setData_compra(compraRequest.getData());
                compra.setCpf(compraRequest.getCpf());
                compra.setStatus("EM PROCESSAMENTO");
                compra.setValor_total_compra(0.0);
                for (Map.Entry<String,Integer> entry : compraRequest.getProdutos().entrySet()){
                    Produto produto = ProdutoService.getProduct(entry, kafkaDTO.getToken());
                    ProdutoComprado produtoComprado = new ProdutoComprado(produto.getCodigo(),produto.getNome(),produto.getPreco(),compraRequest.getProdutos().get(entry.getKey()));
                    compra.getProdutos().add(produtoComprado);
                    sum_values += produto.getPreco()*entry.getValue();

                }

                compra.setValor_total_compra(sum_values);

                sendKafkaMessage.sendMessage(kafkaDTO);
                compraRepository.save(compra);


            }else{
                throw new BadRequest("Codigo do produto invalido.");
            }
        }
    }


