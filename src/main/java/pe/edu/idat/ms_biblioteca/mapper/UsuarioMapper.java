package pe.edu.idat.ms_biblioteca.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pe.edu.idat.ms_biblioteca.dto.request.UsuarioRequestDto;
import pe.edu.idat.ms_biblioteca.dto.response.UsuarioResponseDto;
import pe.edu.idat.ms_biblioteca.entity.Usuario;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    @Mapping(target = "idUsuario", ignore = true)
    @Mapping(target = "estado", ignore = true)
    @Mapping(target = "fechaCreacion", ignore = true)
    @Mapping(target = "fechaModificacion", ignore = true)
    @Mapping(target = "creadoPor", ignore = true)
    @Mapping(target = "modificadoPor", ignore = true)
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "prestamos", ignore = true)
    Usuario toEntity(UsuarioRequestDto usuarioRequestDto);

    @Mapping(target = "idRoles", expression = "java(usuario.getRoles().stream().map(rol -> rol.getIdRol()).collect(java.util.stream.Collectors.toSet()))")
    UsuarioResponseDto toResponse(Usuario usuario);

    List<UsuarioResponseDto> toResponseList(List<Usuario> usuarios);
}