package pe.edu.idat.ms_biblioteca.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioRequestDto {

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 50, message = "El nombre no debe superar los 50 caracteres")
    private String nombre;

    @NotBlank(message = "El apellido paterno es obligatorio")
    @Size(max = 30, message = "El apellido paterno no debe superar los 30 caracteres")
    private String apellidoPaterno;

    @NotBlank(message = "El apellido materno es obligatorio")
    @Size(max = 30, message = "El apellido materno no debe superar los 30 caracteres")
    private String apellidoMaterno;

    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "El correo no tiene un formato válido")
    @Size(max = 60, message = "El correo no debe superar los 60 caracteres")
    private String correo;

    @NotBlank(message = "La contraseña es obligatoria")
    private String contrasena;

    @NotEmpty(message = "Debe asignar al menos un rol")
    private Set<Long> idRoles;
}