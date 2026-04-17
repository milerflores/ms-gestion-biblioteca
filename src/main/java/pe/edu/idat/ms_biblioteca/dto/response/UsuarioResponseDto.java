package pe.edu.idat.ms_biblioteca.dto.response;

import java.time.LocalDate;
import java.util.Set;

public record UsuarioResponseDto(
        Long idUsuario,
        String nombre,
        String apellidoPaterno,
        String apellidoMaterno,
        String correo,
        Integer estado,
        Set<Long> idRoles,
        LocalDate fechaCreacion,
        LocalDate fechaModificacion)
{}