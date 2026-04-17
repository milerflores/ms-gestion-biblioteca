package pe.edu.idat.ms_biblioteca.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PrestamoRequestDto {

    @NotNull(message = "El usuario es obligatorio")
    private Long idUsuario;

    @NotNull(message = "La fecha de préstamo es obligatoria")
    private LocalDate fechaPrestamo;

    private LocalDate fechaDevolucion;

    private Integer estado;

    @NotNull(message = "El detalle del préstamo es obligatorio")
    @Size(min = 1, message = "Debe incluir al menos un libro")
    private List<PrestamoDetalleRequestDto> detalles;
}
