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
//@Entity(name = "produto")
public class Produto {

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "codigo")
    private String codigo;

//    @Column(name = "nome")
    private String nome;

//    @Column(name = "preco")
    private Double preco;

//    @Column(name = "qtde_disponivel")
    private Integer qtde_disponivel;

    public Produto(String codigo, String nome, Double preco) {
        this.codigo = codigo;
        this.nome = nome;
        this.preco = preco;
    }
}
