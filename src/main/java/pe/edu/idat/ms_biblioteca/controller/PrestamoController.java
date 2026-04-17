package pe.edu.idat.ms_biblioteca.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import pe.edu.idat.ms_biblioteca.dto.request.PrestamoRequestDto;
import pe.edu.idat.ms_biblioteca.dto.response.ApiResponse;
import pe.edu.idat.ms_biblioteca.dto.response.PrestamoResponseDto;
import pe.edu.idat.ms_biblioteca.service.impl.PrestamoService;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/prestamos")
public class PrestamoController {

    private final PrestamoService prestamoService;

    @PreAuthorize("hasAnyRole('ADMIN', 'USUARIO')")
    @GetMapping("/historial")
    public ResponseEntity<ApiResponse<List<PrestamoResponseDto>>> listarHistorial(@AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        log.info("Cargando historial para el usuario: {}", username);

        return ResponseEntity.ok(ApiResponse.ok(prestamoService.listarHistorialPorUsuario(username)));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{idPrestamo}")
    public ResponseEntity<ApiResponse<PrestamoResponseDto>> findById(@PathVariable Long idPrestamo) {
        return ResponseEntity.ok(ApiResponse.ok(prestamoService.findById(idPrestamo)));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<ApiResponse<List<PrestamoResponseDto>>> findByUsuario(@PathVariable Long idUsuario) {
        return ResponseEntity.ok(ApiResponse.ok(prestamoService.findByUsuario(idUsuario)));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ApiResponse<PrestamoResponseDto>> create(@Valid @RequestBody PrestamoRequestDto prestamoRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.ok("Se ha creado el préstamo", prestamoService.create(prestamoRequestDto)));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{idPrestamo}")
    public ResponseEntity<ApiResponse<PrestamoResponseDto>> update(@PathVariable Long idPrestamo, @Valid @RequestBody PrestamoRequestDto prestamoRequestDto) {
        return ResponseEntity.ok(ApiResponse.ok(prestamoService.update(idPrestamo, prestamoRequestDto)));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{idPrestamo}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long idPrestamo) {
        prestamoService.delete(idPrestamo);
        return ResponseEntity.ok(ApiResponse.ok("Préstamo eliminado correctamente", null));
    }
}