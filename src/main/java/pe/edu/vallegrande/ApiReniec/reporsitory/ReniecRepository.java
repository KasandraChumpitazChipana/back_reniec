package pe.edu.vallegrande.ApiReniec.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import pe.edu.vallegrande.ApiReniec.model.Reniec;
import reactor.core.publisher.Flux;

@Repository
public interface ReniecRepository extends ReactiveCrudRepository<Reniec, Long> {
    Flux<Reniec> findByStatus(String status);
}
