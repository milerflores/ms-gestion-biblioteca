package pe.edu.idat.ms_biblioteca.service.impl;

import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pe.edu.idat.ms_biblioteca.dto.request.RolRequestDto;
import pe.edu.idat.ms_biblioteca.dto.response.RolResponseDto;
import pe.edu.idat.ms_biblioteca.entity.Rol;
import pe.edu.idat.ms_biblioteca.exception.ResourceConflictException;
import pe.edu.idat.ms_biblioteca.mapper.RolMapper;
import pe.edu.idat.ms_biblioteca.repository.RolRepository;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RolServiceImpl implements RolService {

    private final RolRepository rolRepository;
    private final RolMapper rolMapper;

    @Override
    @Transactional(readOnly = true)
    public List<RolResponseDto> findAll() {
        return rolMapper.toResponseList(rolRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public RolResponseDto findById(Long idRol) {
        Rol rol = rolRepository.findById(idRol)
                .orElseThrow(() -> new ResourceConflictException("Rol no encontrado con id: " + idRol));
        return rolMapper.toResponse(rol);
    }

    @Override
    @Transactional
    public RolResponseDto create(RolRequestDto rolRequestDto) {
        log.info("Creando rol: {}", rolRequestDto);
        Rol rol = rolMapper.toEntity(rolRequestDto);
        rol.setEstado(1);
        return rolMapper.toResponse(rolRepository.save(rol));
    }

    @Override
    @Transactional
    public RolResponseDto update(Long idRol, RolRequestDto rolRequestDto) {
        Rol rol = rolRepository.findById(idRol)
                .orElseThrow(() -> new ResourceConflictException("Rol no encontrado con id: " + idRol));
        log.info("Actualizando rol: {}", rolRequestDto);
        rol.setNombre(rolRequestDto.getNombre());
        rol.setDescripcion(rolRequestDto.getDescripcion());
        return rolMapper.toResponse(rolRepository.save(rol));
    }

    @Override
    @Transactional
    public void delete(Long idRol) {
        if (!rolRepository.existsById(idRol)) {
            throw new ResourceConflictException("Rol no encontrado con id: " + idRol);
        }
        log.info("Eliminando rol con id: {}", idRol);
        rolRepository.deleteById(idRol);
    }
}