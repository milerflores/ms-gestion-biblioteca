package pe.edu.idat.ms_biblioteca.service.impl;

import pe.edu.idat.ms_biblioteca.dto.request.UsuarioRequestDto;
import pe.edu.idat.ms_biblioteca.dto.response.UsuarioResponseDto;

import java.util.List;

public interface UsuarioService {
    List<UsuarioResponseDto> findAll();
    UsuarioResponseDto findById(Long idUsuario);
    UsuarioResponseDto create(UsuarioRequestDto usuarioRequestDto);
    UsuarioResponseDto update(Long idUsuario, UsuarioRequestDto usuarioRequestDto);
    void delete(Long idUsuario);
}
