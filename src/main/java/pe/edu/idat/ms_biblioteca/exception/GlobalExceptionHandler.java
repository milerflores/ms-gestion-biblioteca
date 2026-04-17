package pe.edu.idat.ms_biblioteca.exception;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pe.edu.idat.ms_biblioteca.dto.ErrorResponse;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgNoValid(MethodArgumentNotValidException ex) {
        String detalle = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(e -> e.getField() + ": " + e.getDefaultMessage())
                .collect(Collectors.joining(", "));
        log.warn("Validación fallida: {}", detalle);
        return ResponseEntity.badRequest()
                .body(new ErrorResponse(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(), "Datos Incorrectos", detalle));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolation(ConstraintViolationException ex) {
        String detalle = ex.getConstraintViolations()
                .stream()
                .map(cv -> cv.getPropertyPath() + ": " + cv.getMessage())
                .collect(Collectors.joining(", "));
        log.warn("Constraint violation: {}", detalle);
        return ResponseEntity.badRequest()
                .body(new ErrorResponse(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(), "Parámetro inválido", detalle));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgument(IllegalArgumentException ex) {
        log.warn("Argumento ilegal: {}", ex.getMessage());
        return ResponseEntity.badRequest()
                .body(new ErrorResponse(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(), "Argumento inválido", ex.getMessage()));
    }

    @ExceptionHandler(ResourceConflictException.class)
    public ResponseEntity<ErrorResponse> handleResourceConflict(ResourceConflictException ex) {
        log.warn("Recurso no encontrado: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(), "Recurso no encontrado", ex.getMessage()));
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleBadCredentials(BadCredentialsException ex) {
        log.warn("Credenciales incorrectas");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new ErrorResponse(LocalDateTime.now(), HttpStatus.UNAUTHORIZED.value(), "Autenticación Fallida", "Correo o contraseña incorrectos"));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public void handleAccessDeniedException(AccessDeniedException ex) throws AccessDeniedException {
        throw ex;
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneral(Exception ex) {
        if (ex instanceof AccessDeniedException) throw (AccessDeniedException) ex;
        log.error("Error interno no controlado", ex);
        return ResponseEntity.internalServerError()
                .body(new ErrorResponse(LocalDateTime.now(), HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error Interno", "Ocurrió un error inesperado."));
    }
}