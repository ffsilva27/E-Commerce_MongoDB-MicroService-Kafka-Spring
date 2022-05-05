package br.com.letscode.produto.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter @Setter
public class ProdutoRequest {
   @NotEmpty(message = "Campo nome é de preenchimento obrigatório.")
    private String nome;
    @NotNull(message = "Campo preço é de preenchimento obrigatório.")
    private Double preco;
    @NotNull(message = "Campo quantidade é de preenchimento obrigatório.")
    private Integer qtdeDisponivel;
}
