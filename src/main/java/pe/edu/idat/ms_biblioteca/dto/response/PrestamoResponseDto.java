package pe.edu.idat.ms_biblioteca.dto.response;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
public record PrestamoResponseDto (
        Long idPrestamo,
        Long idUsuario,
        LocalDate fechaPrestamo,
        LocalDate fechaDevolucion,
        Integer estado,
        List<PrestamoDetalleResponseDto> detalles) {

}
