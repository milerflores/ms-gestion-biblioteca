package pe.edu.idat.ms_biblioteca.service.impl;

import pe.edu.idat.ms_biblioteca.dto.request.LibroRequestDto;
import pe.edu.idat.ms_biblioteca.dto.response.LibroResponseDto;

import java.util.List;

public interface LibroService {
    List<LibroResponseDto> findAll();
    LibroResponseDto findById(Long idLibro);
    List<LibroResponseDto> findByTitulo(String titulo);
    LibroResponseDto create(LibroRequestDto libroRequestDto);
    LibroResponseDto update(Long idLibro, LibroRequestDto libroRequestDto);
    void delete(Long idLibro);
}
