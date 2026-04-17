package pe.edu.idat.ms_biblioteca.service.impl;

import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pe.edu.idat.ms_biblioteca.dto.request.PrestamoRequestDto;
import pe.edu.idat.ms_biblioteca.dto.response.PrestamoResponseDto;
import pe.edu.idat.ms_biblioteca.entity.Libro;
import pe.edu.idat.ms_biblioteca.entity.Prestamo;
import pe.edu.idat.ms_biblioteca.entity.PrestamoDetalle;
import pe.edu.idat.ms_biblioteca.entity.Usuario;
import pe.edu.idat.ms_biblioteca.exception.ResourceConflictException;
import pe.edu.idat.ms_biblioteca.mapper.PrestamoMapper;
import pe.edu.idat.ms_biblioteca.repository.LibroRepository;
import pe.edu.idat.ms_biblioteca.repository.PrestamoRepository;
import pe.edu.idat.ms_biblioteca.repository.UsuarioRepository;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class PrestamoServiceImpl implements PrestamoService {

    private final PrestamoRepository prestamoRepository;
    private final UsuarioRepository usuarioRepository;
    private final LibroRepository libroRepository;
    private final PrestamoMapper prestamoMapper;

    @Override
    @Transactional(readOnly = true)
    public List<PrestamoResponseDto> listarTodos() {
        return prestamoMapper.toResponseList(prestamoRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public List<PrestamoResponseDto> listarHistorialPorUsuario(String email) {
        Usuario usuario = usuarioRepository.findByCorreo(email)
                .orElseThrow(() -> new ResourceConflictException("Usuario no encontrado con email: " + email));

        boolean isAdmin = usuario.getRoles().stream()
                .anyMatch(rol -> rol.getNombre().contains("ADMIN"));

        if (isAdmin) {
            return listarTodos();
        }

        return prestamoMapper.toResponseList(
                prestamoRepository.findByUsuario_IdUsuario(usuario.getIdUsuario())
        );
    }

    @Override
    @Transactional(readOnly = true)
    public PrestamoResponseDto findById(Long idPrestamo) {
        Prestamo prestamo = prestamoRepository.findById(idPrestamo)
                .orElseThrow(() -> new ResourceConflictException("Préstamo no encontrado con id: " + idPrestamo));
        return prestamoMapper.toResponse(prestamo);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PrestamoResponseDto> findByUsuario(Long idUsuario) {
        return prestamoMapper.toResponseList(
                prestamoRepository.findByUsuario_IdUsuario(idUsuario)
        );
    }

    @Override
    @Transactional
    public PrestamoResponseDto create(PrestamoRequestDto prestamoRequestDto) {
        log.info("Creando préstamo: {}", prestamoRequestDto);

        Usuario usuario = usuarioRepository.findById(prestamoRequestDto.getIdUsuario())
                .orElseThrow(() -> new ResourceConflictException("Usuario no encontrado con id: " + prestamoRequestDto.getIdUsuario()));

        Prestamo prestamo = new Prestamo();
        prestamo.setUsuario(usuario);
        prestamo.setFechaPrestamo(prestamoRequestDto.getFechaPrestamo());
        prestamo.setFechaDevolucion(prestamoRequestDto.getFechaDevolucion());
        prestamo.setEstado(1);

        List<PrestamoDetalle> detalles = prestamoRequestDto.getDetalles().stream()
                .map(detalleDto -> {
                    Libro libro = libroRepository.findById(detalleDto.getIdLibro())
                            .orElseThrow(() -> new ResourceConflictException("Libro no encontrado con id: " + detalleDto.getIdLibro()));

                    int cantidad = detalleDto.getCantidad();

                    log.info("Stock antes: {}", libro.getStock());

                    if (libro.getStock() < cantidad) {
                        throw new ResourceConflictException("Stock insuficiente para el libro: " + libro.getTitulo());
                    }

                    libro.setStock(libro.getStock() - cantidad);
                    log.info("Stock después: {}", libro.getStock());

                    PrestamoDetalle detalle = new PrestamoDetalle();
                    detalle.setLibro(libro);
                    detalle.setPrestamo(prestamo);
                    detalle.setCantidad(cantidad);

                    return detalle;
                }).toList();

        prestamo.setDetalles(detalles);
        return prestamoMapper.toResponse(prestamoRepository.save(prestamo));
    }

    @Override
    @Transactional
    public PrestamoResponseDto update(Long idPrestamo, PrestamoRequestDto prestamoRequestDto) {
        Prestamo prestamo = prestamoRepository.findById(idPrestamo)
                .orElseThrow(() -> new ResourceConflictException("Préstamo no encontrado con id: " + idPrestamo));

        log.info("Actualizando préstamo: {}", prestamoRequestDto);

        // Devolver stock anterior
        for (PrestamoDetalle det : prestamo.getDetalles()) {
            Libro libro = det.getLibro();
            libro.setStock(libro.getStock() + det.getCantidad());
            libroRepository.save(libro);
        }

        prestamo.setFechaDevolucion(prestamoRequestDto.getFechaDevolucion());
        prestamo.setEstado(prestamoRequestDto.getEstado());

        // Nuevos detalles
        prestamo.getDetalles().clear();

        List<PrestamoDetalle> nuevosDetalles = prestamoRequestDto.getDetalles().stream()
                .map(detalleDto -> {
                    Libro libro = libroRepository.findById(detalleDto.getIdLibro())
                            .orElseThrow(() -> new ResourceConflictException("Libro no encontrado con id: " + detalleDto.getIdLibro()));

                    int cantidad = detalleDto.getCantidad();

                    if (libro.getStock() < cantidad) {
                        throw new ResourceConflictException("Stock insuficiente para el libro: " + libro.getTitulo());
                    }

                    libro.setStock(libro.getStock() - cantidad);
                    libroRepository.save(libro);

                    PrestamoDetalle detalle = new PrestamoDetalle();
                    detalle.setLibro(libro);
                    detalle.setPrestamo(prestamo);
                    detalle.setCantidad(cantidad);

                    return detalle;
                }).toList();

        prestamo.getDetalles().addAll(nuevosDetalles);
        return prestamoMapper.toResponse(prestamoRepository.save(prestamo));
    }

    @Override
    @Transactional
    public void delete(Long idPrestamo) {
        Prestamo prestamo = prestamoRepository.findById(idPrestamo)
                .orElseThrow(() -> new ResourceConflictException("Préstamo no encontrado con id: " + idPrestamo));

        // Devolver stock
        for (PrestamoDetalle det : prestamo.getDetalles()) {
            Libro libro = det.getLibro();
            libro.setStock(libro.getStock() + det.getCantidad());
            libroRepository.save(libro);
        }

        log.info("Eliminando préstamo con id: {}", idPrestamo);
        prestamoRepository.delete(prestamo);
    }
}