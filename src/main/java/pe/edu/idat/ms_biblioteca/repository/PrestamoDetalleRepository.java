package pe.edu.idat.ms_biblioteca.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.idat.ms_biblioteca.entity.PrestamoDetalle;

import java.util.List;

@Repository
public interface PrestamoDetalleRepository extends JpaRepository<PrestamoDetalle, Long> {
    List<PrestamoDetalle> findByPrestamo_IdPrestamo(Long idPrestamo);
}
