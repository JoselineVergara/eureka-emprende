package com.example.eureka.autoevaluacion.domain.service;

import com.example.eureka.entrepreneurship.domain.model.OpcionRespuesta;
import com.example.eureka.formulario.port.out.IOpcionRespuestaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ValoracionCalculator {

    private final IOpcionRespuestaRepository opcionRespuestaRepository;

    public Double calcularPromedioValoracion(Integer idRespuestaValoracion) {
        List<OpcionRespuesta> opciones = opcionRespuestaRepository
                .findByRespuestaId(idRespuestaValoracion);

        double suma = opciones.stream()
                .filter(o -> o.getValorescala() != null)
                .mapToDouble(OpcionRespuesta::getValorescala)
                .sum();

        long cantidad = opciones.stream()
                .filter(o -> o.getValorescala() != null)
                .count();

        return cantidad > 0 ? suma / cantidad : 0.0;
    }
}
