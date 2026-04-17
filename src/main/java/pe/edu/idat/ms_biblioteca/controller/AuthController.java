package pe.edu.idat.ms_biblioteca.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.idat.ms_biblioteca.dto.auth.AuthRequest;
import pe.edu.idat.ms_biblioteca.dto.auth.AuthResponse;
import pe.edu.idat.ms_biblioteca.dto.auth.RegistroRequest;
import pe.edu.idat.ms_biblioteca.service.impl.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/register")
    public ResponseEntity<String> registrar(@RequestBody RegistroRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(authService.registrar(request));
    }
}