package pe.edu.idat.ms_biblioteca.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Set;

@Schema(description = "Información del login con el token JWT")
public record AuthResponse(

        @Schema(description = "Token JWT Bearer")
        String token,

        @Schema(description = "Bearer")
        String tipo,

        @Schema(description = "correo del usuario autenticado")
        String correo,

        @Schema(description = "roles del usuario")
        Set<String> roles,

        @Schema(description = "Tiempo de expiración en milisegundos", example = "36000000")
        long expiracionms

) {
    public static AuthResponse of(String token, String correo, Set<String> roles, long expiracionms) {
        return new AuthResponse(token, "Bearer", correo, roles, expiracionms);
    }
}