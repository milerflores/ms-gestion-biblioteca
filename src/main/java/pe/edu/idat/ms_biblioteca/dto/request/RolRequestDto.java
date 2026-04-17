package pe.edu.idat.ms_biblioteca.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RolRequestDto
{
    @NotBlank(message = "El nombre del rol es obligatorio")
    @Size(max = 30, message = "El nombre no debe superar los 30 caracteres")
    private String nombre;

    @Size(max = 100, message = "La descripción no debe superar los 100 caracteres")
    private String descripcion;
}

