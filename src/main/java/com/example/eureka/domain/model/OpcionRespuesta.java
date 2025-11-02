package com.example.eureka.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "opcion_respuesta")
@IdClass(OpcionRespuesta.OpcionRespuestaId.class)
public class OpcionRespuesta {

    @Id
    @Column(name = "id_respuesta", nullable = false)
    private Long idRespuesta;

    @Id
    @Column(name = "id_opcion", nullable = false)
    private Long idOpcion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_respuesta", insertable = false, updatable = false)
    private Respuesta respuesta;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_opcion", insertable = false, updatable = false)
    private Opciones opcion;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OpcionRespuestaId implements Serializable {
        private Long idRespuesta;
        private Long idOpcion;
    }
}