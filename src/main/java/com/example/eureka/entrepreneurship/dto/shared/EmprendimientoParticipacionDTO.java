package com.example.eureka.entrepreneurship.dto.shared;

import lombok.Data;

@Data
public class EmprendimientoParticipacionDTO {

    private Integer emprendimientoId;

    private Integer opcionParticipacionId;

    private Boolean respuesta;

    private String nombreOpcionParticipacion;
}
