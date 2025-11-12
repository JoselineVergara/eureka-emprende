package com.example.eureka.formulario.domain.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tipo_formulario")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"formularios"})
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class TipoFormulario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tipo")
    @EqualsAndHashCode.Include
    private Long idTipo;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private Boolean activo = true;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    @OneToMany(mappedBy = "tipoFormulario", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Formulario> formularios = new HashSet<>();
}
