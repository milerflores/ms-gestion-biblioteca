package pe.edu.idat.ms_biblioteca.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LibroRequestDto {

    @NotBlank(message = "El título es obligatorio")
    @Size(max = 100, message = "El título no debe superar los 100 caracteres")
    private String titulo;

    @NotBlank(message = "El autor es obligatorio")
    @Size(max = 60, message = "El autor no debe superar los 60 caracteres")
    private String autor;

    @Size(max = 60, message = "La editorial no debe superar los 60 caracteres")
    private String editorial;

    @Size(max = 40, message = "La categoría no debe superar los 40 caracteres")
    private String categoria;

    @NotNull(message = "El stock es obligatorio")
    @Min(value = 0, message = "El stock no puede ser negativo")
    private Integer stock;
}
