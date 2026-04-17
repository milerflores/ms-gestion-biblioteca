package pe.edu.idat.ms_biblioteca.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.idat.ms_biblioteca.dto.auth.AuthRequest;
import pe.edu.idat.ms_biblioteca.dto.auth.AuthResponse;
import pe.edu.idat.ms_biblioteca.dto.auth.RegistroRequest;
import pe.edu.idat.ms_biblioteca.entity.Rol;
import pe.edu.idat.ms_biblioteca.entity.Usuario;
import pe.edu.idat.ms_biblioteca.exception.ResourceConflictException;
import pe.edu.idat.ms_biblioteca.repository.RolRepository;
import pe.edu.idat.ms_biblioteca.repository.UsuarioRepository;
import pe.edu.idat.ms_biblioteca.security.JwtService;
import pe.edu.idat.ms_biblioteca.security.UserDetailsServiceImpl;

import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsServiceImpl userDetailsService;
    private final JwtService jwtService;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final RolRepository rolRepository;

    public AuthResponse login(AuthRequest request) {
        log.info("Iniciando sesión para el usuario: {}", request.getCorreo());
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getCorreo(), request.getContrasena())
        );

        UserDetails user = userDetailsService.loadUserByUsername(request.getCorreo());
        String jwtToken = jwtService.generateToken(user);

        Set<String> roles = user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());

        log.info("Login exitoso para: {} con roles: {}", request.getCorreo(), roles);
        return AuthResponse.of(jwtToken, request.getCorreo(), roles, jwtService.getExpirationTime());
    }

    @Transactional
    public String registrar(RegistroRequest request) {
        // Verificar si el correo ya está registrado
        if (usuarioRepository.existsByCorreo(request.getCorreo())) {
            throw new ResourceConflictException("El correo ya está registrado: " + request.getCorreo());
        }

        String nombreRol = request.getRoles().iterator().next();
        log.info("Registrando usuario con rol: {}", nombreRol);

        Rol rol = rolRepository.findByNombre(nombreRol)
                .orElseThrow(() -> new ResourceConflictException("Rol no encontrado: " + nombreRol));

        Usuario usuario = Usuario.builder()
                .nombre(request.getNombre())
                .apellidoPaterno(request.getApellidoPaterno())
                .apellidoMaterno(request.getApellidoMaterno())
                .correo(request.getCorreo())
                .contrasena(passwordEncoder.encode(request.getContrasena()))
                .estado(1)
                .roles(Set.of(rol))
                .build();

        usuarioRepository.save(usuario);
        log.info("Usuario registrado exitosamente: {}", request.getCorreo());
        return "Usuario registrado exitosamente";
    }
}