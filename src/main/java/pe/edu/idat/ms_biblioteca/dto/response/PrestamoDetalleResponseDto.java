package pe.edu.idat.ms_biblioteca.dto.response;

import lombok.*;

public record PrestamoDetalleResponseDto(
        Long idDetalle,
        Long idLibro,
        String tituloLibro,
        Integer cantidad) {

}
