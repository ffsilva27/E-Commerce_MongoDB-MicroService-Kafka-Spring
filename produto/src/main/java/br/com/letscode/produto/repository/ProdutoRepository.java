package br.com.letscode.produto.repository;


import br.com.letscode.produto.model.Produto;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProdutoRepository extends MongoRepository<Produto, String> {

    Optional<Produto> findByCodigo(String codigo);

}
