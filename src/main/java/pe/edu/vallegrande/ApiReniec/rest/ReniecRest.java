package pe.edu.vallegrande.ApiReniec.rest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pe.edu.vallegrande.ApiReniec.model.Reniec;
import pe.edu.vallegrande.ApiReniec.service.ReniecService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/reniecs")
public class ReniecRest {

    private final ReniecService reniecService;

    public ReniecRest(ReniecService reniecService) {
        this.reniecService = reniecService;
    }

    @GetMapping
    public Flux<Reniec> getAllDniRecords() {
        return reniecService.getAll(); 
    }

    @GetMapping("/dni/{dni}")
    public Mono<Reniec> getDniInfo(@PathVariable String dni) {
        return reniecService.getDniInfo(Long.valueOf(dni)); 
    }

    @GetMapping("/status")
    public Flux<Reniec> getDniByStatus(@RequestParam String status) {
        return reniecService.getByStatus(status); 
    }

    @PutMapping("/restore/{id}")
    public Mono<String> restoreDni(@PathVariable Long id) {
        return reniecService.restoreDni(id); 
    }

    @PutMapping("/update/{id}")
    public Mono<Reniec> updateDni(@PathVariable Long id, @RequestParam String dni) {
        return reniecService.updateDni(id, dni); 
    }

    @PostMapping("/consultar")
    public Mono<Reniec> consultDni(@RequestParam String dni) {
        return reniecService.consultarYGuardarDni(dni); 
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<String> deleteDni(@PathVariable Long id) {
        return reniecService.deleteDni(id); 
    }

    @DeleteMapping("/delete/fisical/{id}")
    public Mono<Void> deleteFisical(@PathVariable Long id) {
        return reniecService.deleteFisical(id); 
    }
}
