package br.com.letscode.compra.model;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Produto {

    private String codigo;
    private String nome;
    private Double preco;
    private Integer qtde_disponivel;

    public Produto(String codigo, String nome, Double preco) {
        this.codigo = codigo;
        this.nome = nome;
        this.preco = preco;
    }
}
