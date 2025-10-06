package com.example.eureka.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "preguntas")
public class Preguntas {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pregunta", nullable = false)
    private Long idPregunta;

    @Column(name = "pregunta", nullable = false)
    private String pregunta;

    @Column(name = "tipo", nullable = false)
    private String tipo;

    @Column(name = "numero_respuestas")
    private Integer numeroRespuestas;

    @Column(name = "estado", nullable = false)
    private String estado;

    @Column(name = "permitir_otro")
    private Boolean permitirOtro;

    @Column(name = "texto_otro")
    private String textoOtro;

    @Column(name = "fecha_creacion", nullable = false)
    private LocalDateTime fechaCreacion;

    @Column(name = "fecha_modificacion")
    private LocalDateTime fechaModificacion;
}