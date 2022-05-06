package br.com.letscode.compra.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "compra")
public class Compra {

    @Id
    private String id;
    private LocalDateTime data_compra;
    private String cpf;
    private Double valor_total_compra;
    private String status;
    private List<ProdutoComprado> produtos =  new ArrayList<>();

}
