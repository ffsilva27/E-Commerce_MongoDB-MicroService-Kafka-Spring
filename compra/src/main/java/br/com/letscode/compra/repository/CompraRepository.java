package br.com.letscode.compra.repository;

import br.com.letscode.compra.model.Compra;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CompraRepository extends MongoRepository<Compra, String> {

    List<Compra> findByCpf(String cpf);

}
