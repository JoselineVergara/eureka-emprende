package com.example.eureka.entrepreneurship.dto;

import com.example.eureka.enums.EstadoEvento;
import com.example.eureka.enums.TipoEvento;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventoResponseDTO {

    private Integer idEvento;
    private String titulo;
    private String descripcion;
    private LocalDateTime fechaEvento;
    private String lugar;
    private TipoEvento tipoEvento;
    private String linkInscripcion;
    private String direccion;
    private EstadoEvento estadoEvento;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaModificacion;
    private Integer idEmprendimiento;
    private String nombreEmprendimiento;
    private Integer idMultimedia;
    private String urlMultimedia;
}
