package com.example.eureka.entrepreneurship.dto;

import com.example.eureka.general.dto.MultimediaDTO;
import lombok.Data;

import java.util.List;

@Data
public class EmprendimientoRequestDTO {

    private Integer usuarioId;

    private EmprendimientoDTO emprendimiento;

    private List<EmprendimientoCategoriaDTO> categorias;

    private List<EmprendimientoDescripcionDTO> descripciones;

    private List<EmprendimientoPresenciaDigitalDTO>  presenciasDigitales;

    private List<MultimediaDTO> imagenes;

    private List<EmprendimientoMetricasDTO>  metricas;

    private List<EmprendimientoParticipacionDTO>  participacionesComunidad;

    private List<EmprendimientoDeclaracionesDTO> declaracionesFinales;
}
