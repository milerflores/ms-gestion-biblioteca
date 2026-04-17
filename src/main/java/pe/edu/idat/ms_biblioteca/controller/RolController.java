package pe.edu.idat.ms_biblioteca.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pe.edu.idat.ms_biblioteca.dto.request.RolRequestDto;
import pe.edu.idat.ms_biblioteca.dto.response.ApiResponse;
import pe.edu.idat.ms_biblioteca.dto.response.RolResponseDto;
import pe.edu.idat.ms_biblioteca.service.impl.RolService;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/roles")
public class RolController {

    private final RolService rolService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<ApiResponse<List<RolResponseDto>>> findAll() {
        return ResponseEntity.ok(ApiResponse.ok(rolService.findAll()));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{idRol}")
    public ResponseEntity<ApiResponse<RolResponseDto>> findById(@PathVariable Long idRol) {
        return ResponseEntity.ok(ApiResponse.ok(rolService.findById(idRol)));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ApiResponse<RolResponseDto>> create(@Valid @RequestBody RolRequestDto rolRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.ok("Se ha creado el rol", rolService.create(rolRequestDto)));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{idRol}")
    public ResponseEntity<ApiResponse<RolResponseDto>> update(@PathVariable Long idRol, @Valid @RequestBody RolRequestDto rolRequestDto) {
        return ResponseEntity.ok(ApiResponse.ok(rolService.update(idRol, rolRequestDto)));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{idRol}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long idRol) {
        rolService.delete(idRol);
        return ResponseEntity.ok(ApiResponse.ok("Rol eliminado correctamente", null));
    }
}