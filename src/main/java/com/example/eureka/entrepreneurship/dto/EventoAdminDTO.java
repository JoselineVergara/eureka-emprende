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
public class EventoAdminDTO {
    private Integer idEvento;
    private String titulo;
    private Integer idEmprendimiento;
    private String nombreEmprendimiento;
    private LocalDateTime fechaEvento;
    private LocalDateTime fechaCreacion;
    private EstadoEvento estadoEvento;
    private TipoEvento tipoEvento;
    private boolean activo;
}