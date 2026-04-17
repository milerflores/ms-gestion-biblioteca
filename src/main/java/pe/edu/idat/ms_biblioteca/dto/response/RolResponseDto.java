package pe.edu.idat.ms_biblioteca.dto.response;

import lombok.*;

import java.math.BigDecimal;

//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
//@Builder
public record RolResponseDto(
        Long idRol,
        String nombre,
        String descripcion,
        Integer estado)
{

}
