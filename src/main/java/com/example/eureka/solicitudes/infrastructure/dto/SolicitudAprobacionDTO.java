package com.example.eureka.solicitudes.infrastructure.dto;

import com.example.eureka.solicitudes.domain.model.SolicitudAprobacion;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SolicitudAprobacionDTO {
    private Integer id;
    private Integer emprendimientoId;
    private String nombreEmprendimiento;
    private SolicitudAprobacion.TipoSolicitud tipoSolicitud;
    private SolicitudAprobacion.EstadoSolicitud estadoSolicitud;
    private String observaciones;
    private String motivoRechazo;
    private LocalDateTime fechaSolicitud;
    private LocalDateTime fechaRespuesta;
    private String nombreSolicitante;
    private String nombreRevisor;
}