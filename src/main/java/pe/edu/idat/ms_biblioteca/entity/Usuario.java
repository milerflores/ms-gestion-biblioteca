package pe.edu.idat.ms_biblioteca.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "tm_usuario")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_tm_usuario")
    @SequenceGenerator(name = "seq_tm_usuario", sequenceName = "seq_tm_usuario", allocationSize = 1)
    @Column(name = "nidusuario")
    private Long idUsuario;

    @Column(name = "snombre", nullable = false, length = 50)
    private String nombre;

    @Column(name = "sapellido_paterno", nullable = false, length = 30)
    private String apellidoPaterno;

    @Column(name = "sapellido_materno", nullable = false, length = 30)
    private String apellidoMaterno;

    @Column(name = "scorreo", nullable = false, unique = true, length = 60)
    private String correo;

    @Column(name = "scontrasena", nullable = false)
    private String contrasena;

    @Column(name = "screado_por", length = 50)
    private String creadoPor;

    @Column(name = "dfecha_creacion")
    private LocalDate fechaCreacion;

    @Column(name = "smodificado_por", length = 50)
    private String modificadoPor;

    @Column(name = "dfecha_modificacion")
    private LocalDate fechaModificacion;

    @Column(name = "nestado", nullable = false)
    private Integer estado;

    @OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Prestamo> prestamos;

    @PrePersist
    public void prePersist(){
        if(this.estado == null){
            this.estado = 1;
        }
    }

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "tt_usuario_rol",
            joinColumns = @JoinColumn(name = "nidusuario"),
            inverseJoinColumns = @JoinColumn(name = "nidrol")
    )

    @Builder.Default
    @JsonIgnore
    private Set<Rol> roles = new HashSet<>();
}
