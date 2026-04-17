package pe.edu.idat.ms_biblioteca.service.impl;

import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pe.edu.idat.ms_biblioteca.dto.request.LibroRequestDto;
import pe.edu.idat.ms_biblioteca.dto.response.LibroResponseDto;
import pe.edu.idat.ms_biblioteca.entity.Libro;
import pe.edu.idat.ms_biblioteca.exception.ResourceConflictException;
import pe.edu.idat.ms_biblioteca.mapper.LibroMapper;
import pe.edu.idat.ms_biblioteca.repository.LibroRepository;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class LibroServiceImpl implements LibroService {

    private final LibroRepository libroRepository;
    private final LibroMapper libroMapper;

    @Override
    @Transactional(readOnly = true)
    public List<LibroResponseDto> findAll() {
        return libroMapper.toResponseList(libroRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public LibroResponseDto findById(Long idLibro) {
        Libro libro = libroRepository.findById(idLibro)
                .orElseThrow(() -> new ResourceConflictException("Libro no encontrado con id: " + idLibro));
        return libroMapper.toResponse(libro);
    }

    @Override
    @Transactional(readOnly = true)
    public List<LibroResponseDto> findByTitulo(String titulo) {
        return libroMapper.toResponseList(libroRepository.findByTituloContainingIgnoreCase(titulo));
    }

    @Override
    @Transactional
    public LibroResponseDto create(LibroRequestDto libroRequestDto) {
        log.info("Creando libro: {}", libroRequestDto);
        Libro libro = libroMapper.toEntity(libroRequestDto);
        return libroMapper.toResponse(libroRepository.save(libro));
    }

    @Override
    @Transactional
    public LibroResponseDto update(Long idLibro, LibroRequestDto libroRequestDto) {
        Libro libro = libroRepository.findById(idLibro)
                .orElseThrow(() -> new ResourceConflictException("Libro no encontrado con id: " + idLibro));
        log.info("Actualizando libro: {}", libroRequestDto);
        libro.setTitulo(libroRequestDto.getTitulo());
        libro.setAutor(libroRequestDto.getAutor());
        libro.setEditorial(libroRequestDto.getEditorial());
        libro.setCategoria(libroRequestDto.getCategoria());
        libro.setStock(libroRequestDto.getStock());
        return libroMapper.toResponse(libroRepository.save(libro));
    }

    @Override
    @Transactional
    public void delete(Long idLibro) {
        if (!libroRepository.existsById(idLibro)) {
            throw new ResourceConflictException("Libro no encontrado con id: " + idLibro);
        }
        log.info("Eliminando libro con id: {}", idLibro);
        libroRepository.deleteById(idLibro);
    }
}