package pe.edu.vallegrande.ApiReniec.reporsitory;


import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import pe.edu.vallegrande.ApiReniec.model.Reniec;

public interface ReniecRepository extends ReactiveCrudRepository<Reniec, String> { // Cambié Long a String
}