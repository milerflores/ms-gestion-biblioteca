package pe.edu.idat.ms_biblioteca.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "tt_prestamo_detalle")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PrestamoDetalle {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_tt_prestamo_detalle")
    @SequenceGenerator(name = "seq_tt_prestamo_detalle", sequenceName = "seq_tt_prestamo_detalle", allocationSize = 1)
    @Column(name = "niddetalle")
    private Long idDetalle;

    @Column(name = "ncantidad", nullable = false)
    private Integer cantidad;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nidprestamo", nullable = false)
    @JsonIgnore
    private Prestamo prestamo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nidlibro", nullable = false)
    private Libro libro;

    @PrePersist
    public void prePersist(){
        if(this.cantidad == null){
            this.cantidad = 1;
        }
    }
}
