package com.example.eureka.valoracion.port.out;

import com.example.eureka.valoracion.domain.model.OpcionRespuesta;

import java.util.List;

public interface IOpcionRespuestaRepository {

    OpcionRespuesta save(OpcionRespuesta opcionRespuesta);

    List<OpcionRespuesta> saveAll(List<OpcionRespuesta> opcionesRespuesta);

    List<OpcionRespuesta> findByRespuestaId(Long idRespuesta);
}