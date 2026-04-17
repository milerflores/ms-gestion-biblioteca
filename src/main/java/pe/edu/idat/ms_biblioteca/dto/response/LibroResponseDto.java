package pe.edu.idat.ms_biblioteca.dto.response;

import lombok.*;

//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
//@Builder
public record LibroResponseDto(
        Long idLibro,
        String titulo,
        String autor,
        String editorial,
        String categoria,
        Integer stock,
        Integer estado

){}
//    private Long idLibro;
//    private String titulo;
//    private String autor;
//    private String editorial;
//    private String categoria;
//    private Integer stock;
//    private Integer estado;

