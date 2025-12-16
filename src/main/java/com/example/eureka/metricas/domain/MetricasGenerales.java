package com.example.eureka.metricas.domain;



import com.example.eureka.entrepreneurship.domain.model.Emprendimientos;
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
@Table(name = "metricas_generales")
public class MetricasGenerales {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "emprendimiento_id", nullable = false)
    private Emprendimientos emprendimientos;

    @Column(name = "vistas", nullable = false)
    private Integer vistas;

    @Column(name = "nivel_valoracion")
    private Integer nivelValoracion;

    @Column(name = "fecha_registro", nullable = false)
    private LocalDateTime fechaRegistro;


}
