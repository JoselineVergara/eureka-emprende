package com.example.eureka.formulario.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "formulario_preguntas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FormularioPregunta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relación con Formulario
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_formulario", nullable = false)
    private Formulario formulario;

    // Relación con Pregunta
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_pregunta", nullable = false)
    private Pregunta pregunta;

    @Column(nullable = false)
    private Integer orden;

    @Column(nullable = false)
    private Boolean obligatoria = true;
}