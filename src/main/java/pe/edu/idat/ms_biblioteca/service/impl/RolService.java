package pe.edu.idat.ms_biblioteca.service.impl;

import pe.edu.idat.ms_biblioteca.dto.request.RolRequestDto;
import pe.edu.idat.ms_biblioteca.dto.response.RolResponseDto;

import java.util.List;

public interface RolService {
    List<RolResponseDto> findAll();
    RolResponseDto findById(Long idRol);
    RolResponseDto create(RolRequestDto rolRequestDto);
    RolResponseDto update(Long idRol, RolRequestDto rolRequestDto);
    void delete(Long idRol);
}
