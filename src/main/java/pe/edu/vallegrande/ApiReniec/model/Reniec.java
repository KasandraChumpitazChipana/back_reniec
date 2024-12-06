package pe.edu.vallegrande.ApiReniec.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id; // Corregido el import de Id
import org.springframework.data.relational.core.mapping.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table("usuarios")
public class Reniec {
    @Id
    private String dni; // DNI como identificador

    private String nombres;

    private String apellidoPaterno; // Cambié a camelCase

    private String apellidoMaterno; // Cambié a camelCase
}
