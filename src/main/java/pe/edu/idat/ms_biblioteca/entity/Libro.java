package pe.edu.idat.ms_biblioteca.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "tm_libro")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_tm_libro")
    @SequenceGenerator(name = "seq_tm_libro", sequenceName = "seq_tm_libro", allocationSize = 1)
    @Column(name = "nidlibro")
    private Long idLibro;

    @Column(name = "stitulo", nullable = false, length = 100)
    private String titulo;

    @Column(name = "sautor", nullable = false, length = 60)
    private String autor;

    @Column(name = "seditorial", length = 60)
    private String editorial;

    @Column(name = "scategoria", length = 40)
    private String categoria;

    @Column(name = "nstock", nullable = false)
    private Integer stock;

    @Column(name = "nestado", nullable = false)
    private Integer estado;

    @OneToMany(mappedBy = "libro", fetch = FetchType.LAZY)
    private List<PrestamoDetalle> prestamoDetalles;

    @PrePersist
    public void prePersist(){
        if(this.estado == null){
            this.estado = 1;
        }
    }
}

