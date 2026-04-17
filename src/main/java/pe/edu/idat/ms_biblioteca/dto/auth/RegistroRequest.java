package pe.edu.idat.ms_biblioteca.dto.auth;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import pe.edu.idat.ms_biblioteca.repository.RolRepository;

import java.util.Set;

@Data
public class RegistroRequest {
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String correo;
    private String contrasena;
    private Set<String> roles; // Ejemplo: ["ADMIN", "USER"]
}

