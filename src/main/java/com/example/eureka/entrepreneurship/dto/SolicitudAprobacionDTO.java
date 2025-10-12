package com.example.eureka.entrepreneurship.dto;

import com.example.eureka.model.SolicitudAprobacion;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SolicitudAprobacionDTO {
    private Long id;
    private Integer emprendimientoId;
    private String nombreEmprendimiento;
    private SolicitudAprobacion.TipoSolicitud tipoSolicitud;
    private SolicitudAprobacion.EstadoSolicitud estadoSolicitud;
    private Map<String, Object> datosPropuestos;
    private Map<String, Object> datosOriginales;
    private String observaciones;
    private String motivoRechazo;
    private LocalDateTime fechaSolicitud;
    private LocalDateTime fechaRespuesta;
    private String nombreSolicitante;
    private String nombreRevisor;
}