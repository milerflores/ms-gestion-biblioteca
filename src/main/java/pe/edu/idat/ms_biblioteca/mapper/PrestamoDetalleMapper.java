package pe.edu.idat.ms_biblioteca.mapper;



import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pe.edu.idat.ms_biblioteca.dto.request.PrestamoDetalleRequestDto;
import pe.edu.idat.ms_biblioteca.dto.response.PrestamoDetalleResponseDto;
import pe.edu.idat.ms_biblioteca.entity.PrestamoDetalle;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PrestamoDetalleMapper {

    @Mapping(target = "idDetalle", ignore = true)
    @Mapping(target = "prestamo", ignore = true)
    @Mapping(target = "libro", ignore = true)
    PrestamoDetalle toEntity(PrestamoDetalleRequestDto prestamoDetalleRequestDto);

    @Mapping(target = "idLibro", source = "libro.idLibro")
    @Mapping(target = "tituloLibro", source = "libro.titulo")
    PrestamoDetalleResponseDto toResponse(PrestamoDetalle prestamoDetalle);

    List<PrestamoDetalleResponseDto> toResponseList(List<PrestamoDetalle> detalles);
}