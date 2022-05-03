package br.com.letscode.produto.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "produto")
public class Produto {

    @Id
    private String codigo;
    private String nome;
    private Double preco;
    private Integer qtde_disponivel;

}
