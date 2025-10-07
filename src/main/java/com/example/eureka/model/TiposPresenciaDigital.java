package com.example.eureka.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tipos_presencia_digital")
public class TiposPresenciaDigital {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "plataforma", nullable = false, columnDefinition = "TEXT")
    private String plataforma;

    @Column(name = "descripcion")
    private String descripcion;
}
