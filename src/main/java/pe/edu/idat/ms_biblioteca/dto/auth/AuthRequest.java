package pe.edu.idat.ms_biblioteca.dto.auth;

import lombok.Data;

@Data
public class AuthRequest {
    private String correo;
    private String contrasena;
}