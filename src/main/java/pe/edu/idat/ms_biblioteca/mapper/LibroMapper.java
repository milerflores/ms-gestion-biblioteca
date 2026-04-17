package pe.edu.idat.ms_biblioteca.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pe.edu.idat.ms_biblioteca.dto.request.LibroRequestDto;
import pe.edu.idat.ms_biblioteca.dto.response.LibroResponseDto;
import pe.edu.idat.ms_biblioteca.entity.Libro;

import java.util.List;

@Mapper(componentModel = "spring")
public interface LibroMapper {

    @Mapping(target = "idLibro", ignore = true)
    @Mapping(target = "estado", ignore = true)
    @Mapping(target = "prestamoDetalles", ignore = true)
    Libro toEntity(LibroRequestDto libroRequestDto);

    LibroResponseDto toResponse(Libro libro);

    List<LibroResponseDto> toResponseList(List<Libro> libros);
}
