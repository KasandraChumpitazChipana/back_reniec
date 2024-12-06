package pe.edu.vallegrande.ApiReniec.rest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pe.edu.vallegrande.ApiReniec.model.Reniec;
import pe.edu.vallegrande.ApiReniec.service.ReniecService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/reniecs") // Cambié la ruta para reflejar el nombre del recurso
public class ReniecRest {

    private final ReniecService reniecService;

    // Constructor para inyectar el servicio
    public ReniecRest(ReniecService reniecService) {
        this.reniecService = reniecService;
    }

    // Obtener todos los Reniecs
    @GetMapping
    public Flux<Reniec> getAllReniecs() {
        return reniecService.findAll();
    }

    // Obtener un Reniec por DNI
    @GetMapping("/{dni}")
    public Mono<Reniec> getReniecByDni(@PathVariable String dni) { // Cambié el parámetro a String para usar DNI
        return reniecService.findByDni(dni);
    }

    // Crear un nuevo Reniec
    @PostMapping("/crear")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Reniec> createReniec(@RequestBody Reniec reniec) {
        return reniecService.save(reniec);
    }

    // Actualizar un Reniec existente
    @PutMapping("/{dni}")
    public Mono<Reniec> updateReniec(@PathVariable String dni, @RequestBody Reniec reniec) { // Cambié el parámetro a String para usar DNI
        reniec.setDni(dni); // Asegura que el DNI del Reniec se establezca correctamente
        return reniecService.update(reniec);
    }

    // Eliminar un Reniec por DNI (definitivamente)
    @DeleteMapping("/{dni}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteReniec(@PathVariable String dni) { // Cambié el parámetro a String para usar DNI
        return reniecService.deleteByDni(dni);
    }

}
