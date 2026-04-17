package pe.edu.idat.ms_biblioteca.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "tm_prestamo")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Prestamo {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_tt_prestamo")
    @SequenceGenerator(name = "seq_tt_prestamo", sequenceName = "seq_tt_prestamo", allocationSize = 1)
    @Column(name = "nidprestamo")
    private Long idPrestamo;

    @Column(name = "dfecha_prestamo", nullable = false)
    private LocalDate fechaPrestamo;

    @Column(name = "dfecha_devolucion")
    private LocalDate fechaDevolucion;

    @Column(name = "nestado", nullable = false)
    private Integer estado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nidusuario", nullable = false)
    @JsonIgnore
    private Usuario usuario;

    @OneToMany(mappedBy = "prestamo", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private List<PrestamoDetalle> detalles;

    @PrePersist
    public void prePersist(){
        if(this.estado == null){
            this.estado = 1;
        }
    }
}
