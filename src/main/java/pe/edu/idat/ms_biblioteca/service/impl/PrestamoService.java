package pe.edu.idat.ms_biblioteca.service.impl;

import pe.edu.idat.ms_biblioteca.dto.request.PrestamoRequestDto;
import pe.edu.idat.ms_biblioteca.dto.response.PrestamoResponseDto;

import java.util.List;

public interface PrestamoService {

    List<PrestamoResponseDto> listarTodos();

    List<PrestamoResponseDto> listarHistorialPorUsuario(String email);

    List<PrestamoResponseDto> findByUsuario(Long idUsuario);

    PrestamoResponseDto findById(Long idPrestamo);

    PrestamoResponseDto create(PrestamoRequestDto prestamoRequestDto);

    PrestamoResponseDto update(Long idPrestamo, PrestamoRequestDto prestamoRequestDto);

    void delete(Long idPrestamo);
}