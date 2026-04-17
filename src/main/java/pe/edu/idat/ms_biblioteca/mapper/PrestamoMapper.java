package pe.edu.idat.ms_biblioteca.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pe.edu.idat.ms_biblioteca.dto.request.PrestamoRequestDto;
import pe.edu.idat.ms_biblioteca.dto.response.PrestamoResponseDto;
import pe.edu.idat.ms_biblioteca.entity.Prestamo;

import java.util.List;

@Mapper(componentModel = "spring", uses = {PrestamoDetalleMapper.class})
public interface PrestamoMapper {

    @Mapping(target = "idPrestamo", ignore = true)
    @Mapping(target = "estado", ignore = true)
    @Mapping(target = "usuario", ignore = true)
    @Mapping(target = "detalles", ignore = true)
    Prestamo toEntity(PrestamoRequestDto prestamoRequestDto);

    @Mapping(target = "idUsuario", source = "usuario.idUsuario")
    PrestamoResponseDto toResponse(Prestamo prestamo);

    List<PrestamoResponseDto> toResponseList(List<Prestamo> prestamos);
}
