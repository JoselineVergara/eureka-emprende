package com.example.eureka.formulario.domain.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "formulario")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"formularioPreguntas", "respuestas"})
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Formulario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_formulario")
    @EqualsAndHashCode.Include
    private Long idFormulario;

    @Column(nullable = false)
    private String nombre;

    @Column(name = "fecha_creacion", nullable = false)
    private LocalDateTime fechaCreacion;

    @Column(nullable = false)
    private String estado;

    @Column(name = "fecha_modificacion")
    private LocalDateTime fechaModificacion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tipo_formulario")
    private TipoFormulario tipoFormulario;

    @OneToMany(mappedBy = "formulario", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<FormularioPregunta> formularioPreguntas = new HashSet<>();
}