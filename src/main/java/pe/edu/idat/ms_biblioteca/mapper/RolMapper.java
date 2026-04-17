package pe.edu.idat.ms_biblioteca.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pe.edu.idat.ms_biblioteca.dto.request.RolRequestDto;
import pe.edu.idat.ms_biblioteca.dto.response.RolResponseDto;
import pe.edu.idat.ms_biblioteca.entity.Rol;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RolMapper {

    @Mapping(target = "idRol", ignore = true)
    Rol toEntity(RolRequestDto rolRequestDto);

    RolResponseDto toResponse(Rol rol);

    List<RolResponseDto> toResponseList(List<Rol> roles);
}
