package pe.edu.idat.ms_biblioteca.service.impl;

import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pe.edu.idat.ms_biblioteca.dto.request.UsuarioRequestDto;
import pe.edu.idat.ms_biblioteca.dto.response.UsuarioResponseDto;
import pe.edu.idat.ms_biblioteca.entity.Rol;
import pe.edu.idat.ms_biblioteca.entity.Usuario;
import pe.edu.idat.ms_biblioteca.exception.ResourceConflictException;
import pe.edu.idat.ms_biblioteca.mapper.UsuarioMapper;
import pe.edu.idat.ms_biblioteca.repository.RolRepository;
import pe.edu.idat.ms_biblioteca.repository.UsuarioRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final UsuarioMapper usuarioMapper;

    @Override
    @Transactional(readOnly = true)
    public List<UsuarioResponseDto> findAll() {
        return usuarioMapper.toResponseList(usuarioRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public UsuarioResponseDto findById(Long idUsuario) {
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new ResourceConflictException("Usuario no encontrado con id: " + idUsuario));
        return usuarioMapper.toResponse(usuario);
    }

    @Override
    @Transactional
    public UsuarioResponseDto create(UsuarioRequestDto usuarioRequestDto) {
        Set<Rol> roles = usuarioRequestDto.getIdRoles().stream()
                .map(idRol -> rolRepository.findById(idRol)
                        .orElseThrow(() -> new ResourceConflictException("Rol no encontrado con id: " + idRol)))
                .collect(Collectors.toSet());

        Usuario usuario = usuarioMapper.toEntity(usuarioRequestDto);
        usuario.setRoles(roles);
        usuario.setFechaCreacion(LocalDate.now());
        usuario.setCreadoPor("system");

        log.info("Creando usuario: {} {}", usuarioRequestDto.getNombre(), usuarioRequestDto.getApellidoPaterno());

        return usuarioMapper.toResponse(usuarioRepository.save(usuario));
    }

    @Override
    @Transactional
    public UsuarioResponseDto update(Long idUsuario, UsuarioRequestDto usuarioRequestDto) {
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new ResourceConflictException("Usuario no encontrado con id: " + idUsuario));

        Set<Rol> roles = usuarioRequestDto.getIdRoles().stream()
                .map(idRol -> rolRepository.findById(idRol)
                        .orElseThrow(() -> new ResourceConflictException("Rol no encontrado con id: " + idRol)))
                .collect(Collectors.toSet());

        log.info("Actualizando usuario: {}", usuarioRequestDto);
        usuario.setNombre(usuarioRequestDto.getNombre());
        usuario.setApellidoPaterno(usuarioRequestDto.getApellidoPaterno());
        usuario.setApellidoMaterno(usuarioRequestDto.getApellidoMaterno());
        usuario.setCorreo(usuarioRequestDto.getCorreo());
        usuario.setContrasena(usuarioRequestDto.getContrasena());
        usuario.setRoles(roles);
        usuario.setFechaModificacion(LocalDate.now());
        usuario.setModificadoPor("system");

        return usuarioMapper.toResponse(usuarioRepository.save(usuario));
    }

    @Override
    @Transactional
    public void delete(Long idUsuario) {
        if (!usuarioRepository.existsById(idUsuario)) {
            throw new ResourceConflictException("Usuario no encontrado con id: " + idUsuario);
        }
        log.info("Eliminando usuario con id: {}", idUsuario);
        usuarioRepository.deleteById(idUsuario);
    }
}