package br.com.letscode.compra.model;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ProdutoComprado extends Produto{
    Integer qtd_comprada;
    String statusProduto;
    public ProdutoComprado(String codigo, String nome, Double preco,Integer qtd_comprada) {
        super(codigo, nome, preco);
        this.qtd_comprada = qtd_comprada;
    }

    public ProdutoComprado() {
    }
}
