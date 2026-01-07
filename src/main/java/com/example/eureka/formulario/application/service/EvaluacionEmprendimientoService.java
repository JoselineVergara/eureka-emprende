package com.example.eureka.formulario.application.service;

import com.example.eureka.entrepreneurship.domain.model.Emprendimientos;
import com.example.eureka.entrepreneurship.domain.model.OpcionRespuesta;
import com.example.eureka.autoevaluacion.domain.model.Respuesta;
import com.example.eureka.formulario.infrastructure.dto.response.OpcionRespuestaRequestDTO;
import com.example.eureka.metricas.port.in.MetricasPreguntaService;
import com.example.eureka.notificacion.port.in.NotificacionService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EvaluacionEmprendimientoService {

    private final MetricasPreguntaService metricasPreguntaService;
    private final NotificacionService notificacionService;

    public EvaluacionEmprendimientoService(MetricasPreguntaService metricasPreguntaService,
                                           NotificacionService notificacionService) {
        this.metricasPreguntaService = metricasPreguntaService;
        this.notificacionService = notificacionService;
    }

    public void procesarEvaluacion(Emprendimientos emp,
                                   List<OpcionRespuesta> respuestasGuardadas,
                                   Respuesta cabeceraValoracion,
                                   List<OpcionRespuestaRequestDTO> requests) {

        if (emp == null || respuestasGuardadas == null || respuestasGuardadas.isEmpty() || cabeceraValoracion == null) {
            return;
        }

        double sumaValores = 0;
        int contador = 0;

        for (OpcionRespuestaRequestDTO req : requests) {
            if (req.getValorescala() != null) {
                sumaValores += req.getValorescala();
                contador++;
            }
        }

        double promedio = contador > 0 ? sumaValores / contador : 0;

        // Métricas
        metricasPreguntaService.procesarValoracionPorPreguntas(emp, respuestasGuardadas);

        // Notificación
        if (promedio <= 2) {
            Integer idEmprendedor = emp.getUsuarios().getId();
            Integer idValoracion = cabeceraValoracion.getId();
            String mensaje = "El emprendimiento " + emp.getNombreComercial()
                    + " necesita realizar una autoevaluación debido a la valoración obtenida : " + promedio;

            notificacionService.crearNotificacionAutoevaluacion(
                    idEmprendedor,
                    "AUTOEVALUACION_REQUERIDA",
                    "Autoevaluación requerida",
                    mensaje,
                    idValoracion != null ? idValoracion.toString() : "",
                    emp.getId()
            );
        }
    }
}
