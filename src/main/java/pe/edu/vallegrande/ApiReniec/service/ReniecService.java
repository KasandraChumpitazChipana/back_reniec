package pe.edu.vallegrande.ApiReniec.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import pe.edu.vallegrande.ApiReniec.model.Reniec;
import pe.edu.vallegrande.ApiReniec.repository.ReniecRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import org.json.JSONObject;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ReniecService {

    private final ReniecRepository repository;
    private final WebClient webClient;
    private final String token;

    public ReniecService(WebClient.Builder webClientBuilder, ReniecRepository repository, @Value("${name.token}") String token) {
        this.repository = repository;
        this.token = token;
        this.webClient = webClientBuilder.baseUrl("https://dniruc.apisperu.com/api/v1").build();
    }

    public Mono<Reniec> consultarYGuardarDni(String dni) {
        String url = "/dni/" + dni + "?token=" + token;

        return webClient.get()
                .uri(url)
                .retrieve()
                .bodyToMono(String.class)
                .flatMap(responseBody -> {
                    JSONObject json = new JSONObject(responseBody);

                    if (json.getBoolean("success")) {
                        Reniec reniec = new Reniec();
                        reniec.setDni(json.getString("dni"));
                        reniec.setNombres(json.getString("nombres"));
                        reniec.setApellidoPaterno(json.getString("apellidoPaterno"));
                        reniec.setApellidoMaterno(json.getString("apellidoMaterno"));
                        reniec.setCodVerifica(json.getString("codVerifica"));
                        reniec.setStatus("A"); 

                        return repository.save(reniec);
                    } else {
                        return Mono.error(new RuntimeException("No se pudo obtener información del DNI."));
                    }
                })
                .doOnError(e -> log.error("Error al consultar el DNI: ", e));
    }

    public Mono<Reniec> getDniInfo(Long dni) {
        String url = "/dni/" + dni + "?token=" + token;

        return webClient.get()
                .uri(url)
                .retrieve()
                .bodyToMono(Reniec.class)
                .flatMap(reniecInfo -> {
                    reniecInfo.setDni(String.valueOf(dni)); 
                    return repository.save(reniecInfo);
                })
                .doOnError(e -> log.error("Error while fetching DNI info", e));
    }

    public Mono<Reniec> updateDni(Long id, String dni) {
        return repository.findById(id)
                .flatMap(existingReniec -> {
                    if (existingReniec == null) {
                        return Mono.error(new RuntimeException("DNI no encontrado."));
                    }

                    String url = "/dni/" + dni + "?token=" + token;

                    return webClient.get()
                            .uri(url)
                            .retrieve()
                            .bodyToMono(String.class)
                            .flatMap(responseBody -> {
                                JSONObject json = new JSONObject(responseBody);

                                if (json.getBoolean("success")) {
                                    existingReniec.setDni(json.getString("dni"));
                                    existingReniec.setNombres(json.getString("nombres"));
                                    existingReniec.setApellidoPaterno(json.getString("apellidoPaterno"));
                                    existingReniec.setApellidoMaterno(json.getString("apellidoMaterno"));
                                    existingReniec.setCodVerifica(json.getString("codVerifica"));

                                    return repository.save(existingReniec);
                                } else {
                                    return Mono.error(new RuntimeException("DNI no válido."));
                                }
                            })
                            .onErrorResume(error -> Mono.error(new RuntimeException("Error al consultar el servicio externo.", error)));
                })
                .switchIfEmpty(Mono.error(new RuntimeException("DNI no encontrado.")));
    }

    public Flux<Reniec> getByStatus(String status) {
        return repository.findByStatus(status);
    }

    public Flux<Reniec> getAll() {
        return repository.findAll();
    }

    public Mono<String> deleteDni(Long id) {
        return repository.findById(id)
                .flatMap(existingReniec -> {
                    existingReniec.setStatus("I");
                    return repository.save(existingReniec)
                            .then(Mono.just("DNI eliminada lógicamente con éxito: " + existingReniec.getDni()));
                })
                .switchIfEmpty(Mono.just("DNI no encontrada."));
    }

    public Mono<Void> deleteFisical(Long id) {
        return repository.findById(id)
                .flatMap(existingReniec -> repository.delete(existingReniec));
    }

    public Mono<String> restoreDni(Long id) {
        return repository.findById(id)
                .flatMap(existingReniec -> {
                    existingReniec.setStatus("A");
                    return repository.save(existingReniec)
                            .then(Mono.just("DNI restaurada con éxito: " + existingReniec.getDni()));
                })
                .switchIfEmpty(Mono.just("DNI no encontrada."));
    }
}
