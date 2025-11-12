package com.example.eureka.formulario.domain.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "preguntas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"opciones", "formularioPreguntas"})  // Excluir colecciones del toString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)  // CRÍTICO: Solo incluir ID
public class Pregunta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pregunta")
    @EqualsAndHashCode.Include  // Solo el ID en equals/hashCode
    private Long idPregunta;

    @Column(nullable = false)
    private String pregunta;

    @Column(nullable = false)
    private String tipo;

    @Column(name = "numero_respuestas")
    private Integer numeroRespuestas;

    @Column(nullable = false)
    private String estado;

    @Column(name = "permitir_otro")
    private Boolean permitirOtro;

    @Column(name = "texto_otro")
    private String textoOtro;

    @Column(name = "fecha_creacion", nullable = false)
    private LocalDateTime fechaCreacion;

    @Column(name = "fecha_modificacion")
    private LocalDateTime fechaModificacion;

    // IMPORTANTE: Usar Set en lugar de List para evitar MultipleBagFetchException
    @OneToMany(mappedBy = "pregunta", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Opcion> opciones = new HashSet<>();

    @OneToMany(mappedBy = "pregunta", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<FormularioPregunta> formularioPreguntas = new HashSet<>();
}
