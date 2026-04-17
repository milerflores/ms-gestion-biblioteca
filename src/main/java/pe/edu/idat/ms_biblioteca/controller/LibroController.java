package pe.edu.idat.ms_biblioteca.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pe.edu.idat.ms_biblioteca.dto.request.LibroRequestDto;
import pe.edu.idat.ms_biblioteca.dto.response.ApiResponse;
import pe.edu.idat.ms_biblioteca.dto.response.LibroResponseDto;
import pe.edu.idat.ms_biblioteca.service.impl.LibroService;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/libros")
public class LibroController{

    private final LibroService libroService;

    @PreAuthorize("hasAnyRole('ADMIN', 'USUARIO')")
    @GetMapping
    public ResponseEntity<ApiResponse<List<LibroResponseDto>>> findAll() {
        return ResponseEntity.ok(ApiResponse.ok(libroService.findAll()));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USUARIO')")
    @GetMapping("/{idLibro}")
    public ResponseEntity<ApiResponse<LibroResponseDto>> findById(@PathVariable Long idLibro) {
        return ResponseEntity.ok(ApiResponse.ok(libroService.findById(idLibro)));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USUARIO')")
    @GetMapping("/buscar")
    public ResponseEntity<ApiResponse<List<LibroResponseDto>>> findByTitulo(@RequestParam String titulo) {
        return ResponseEntity.ok(ApiResponse.ok(libroService.findByTitulo(titulo)));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ApiResponse<LibroResponseDto>> create(@Valid @RequestBody LibroRequestDto libroRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.ok("Se ha creado el libro", libroService.create(libroRequestDto)));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{idLibro}")
    public ResponseEntity<ApiResponse<LibroResponseDto>> update(@PathVariable Long idLibro, @Valid @RequestBody LibroRequestDto libroRequestDto) {
        return ResponseEntity.ok(ApiResponse.ok(libroService.update(idLibro, libroRequestDto)));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{idLibro}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long idLibro) {
        libroService.delete(idLibro);
        return ResponseEntity.ok(ApiResponse.ok("Libro eliminado correctamente", null));
    }
}