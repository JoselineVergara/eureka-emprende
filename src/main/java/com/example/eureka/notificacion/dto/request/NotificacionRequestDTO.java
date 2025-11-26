package com.example.eureka.notificacion.dto.request;

import lombok.Data;

import java.util.Map;

@Data
public class NotificacionRequestDTO {

    Integer usuarioId;
    String codigoTipo;
    Map<String, Object> parametros;
    String enlace;
    Integer emprendimientoId;
    Integer solicitudId;


}
