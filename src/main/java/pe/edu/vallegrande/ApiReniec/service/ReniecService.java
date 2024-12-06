package pe.edu.vallegrande.ApiReniec.service; // Asegúrate de que el paquete sea correcto

import org.springframework.stereotype.Service;
import pe.edu.vallegrande.ApiReniec.model.Reniec;
// Corregido el nombre del paquete

import pe.edu.vallegrande.ApiReniec.reporsitory.ReniecRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ReniecService {

    private final ReniecRepository reniecRepository; // Usar final para la inyección de dependencias

    // Constructor para inyectar el repositorio
    public ReniecService(ReniecRepository reniecRepository) {
        this.reniecRepository = reniecRepository;
    }

    // Método para guardar un nuevo Reniec
    public Mono<Reniec> save(Reniec reniec) {
        return reniecRepository.save(reniec);
    }

    // Método para actualizar un Reniec existente
    public Mono<Reniec> update(Reniec reniec) {
        return reniecRepository.save(reniec);
    }

    // Método para eliminar un Reniec por DNI (eliminación definitiva)
    public Mono<Void> deleteByDni(String dni) { // Cambié el parámetro a String para usar DNI
        return reniecRepository.deleteById(dni);
    }


    // Método para encontrar un Reniec por DNI
    public Mono<Reniec> findByDni(String dni) { // Cambié el parámetro a String para usar DNI
        return reniecRepository.findById(dni);
    }

    // Método para obtener todos los Reniecs
    public Flux<Reniec> findAll() {
        return reniecRepository.findAll();
    }
}
