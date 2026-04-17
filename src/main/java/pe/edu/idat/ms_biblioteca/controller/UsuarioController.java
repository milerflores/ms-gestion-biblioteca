package pe.edu.idat.ms_biblioteca.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pe.edu.idat.ms_biblioteca.dto.request.UsuarioRequestDto;
import pe.edu.idat.ms_biblioteca.dto.response.ApiResponse;
import pe.edu.idat.ms_biblioteca.dto.response.UsuarioResponseDto;
import pe.edu.idat.ms_biblioteca.service.impl.UsuarioService;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<ApiResponse<List<UsuarioResponseDto>>> findAll() {
        return ResponseEntity.ok(ApiResponse.ok(usuarioService.findAll()));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{idUsuario}")
    public ResponseEntity<ApiResponse<UsuarioResponseDto>> findById(@PathVariable Long idUsuario) {
        return ResponseEntity.ok(ApiResponse.ok(usuarioService.findById(idUsuario)));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ApiResponse<UsuarioResponseDto>> create(@Valid @RequestBody UsuarioRequestDto usuarioRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.ok("Se ha creado el usuario", usuarioService.create(usuarioRequestDto)));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{idUsuario}")
    public ResponseEntity<ApiResponse<UsuarioResponseDto>> update(@PathVariable Long idUsuario, @Valid @RequestBody UsuarioRequestDto usuarioRequestDto) {
        return ResponseEntity.ok(ApiResponse.ok(usuarioService.update(idUsuario, usuarioRequestDto)));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{idUsuario}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long idUsuario) {
        usuarioService.delete(idUsuario);
        return ResponseEntity.ok(ApiResponse.ok("Usuario eliminado correctamente", null));
    }
}