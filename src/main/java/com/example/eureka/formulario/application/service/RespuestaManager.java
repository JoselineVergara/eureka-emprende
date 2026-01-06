package com.example.eureka.formulario.application.service;

import com.example.eureka.autoevaluacion.domain.model.Respuesta;
import com.example.eureka.autoevaluacion.port.out.IAutoevaluacionRepository;
import com.example.eureka.entrepreneurship.domain.model.Emprendimientos;
import com.example.eureka.formulario.domain.model.Formulario;
import com.example.eureka.formulario.infrastructure.dto.response.OpcionRespuestaRequestDTO;
import com.example.eureka.formulario.port.out.IFormularioRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class RespuestaManager {

    private final IAutoevaluacionRepository autoevaluacionRepository;
    private final IFormularioRepository formularioRepository;

    public RespuestaManager(IAutoevaluacionRepository autoevaluacionRepository,
                            IFormularioRepository formularioRepository) {
        this.autoevaluacionRepository = autoevaluacionRepository;
        this.formularioRepository = formularioRepository;
    }

    public ResultadoCabeceras obtenerRespuestaCabecera(
            OpcionRespuestaRequestDTO opcionRespuesta,
            Emprendimientos emp,
            Respuesta cabeceraValoracion,
            Respuesta cabeceraAutoevaluacion
    ) {

        Respuesta rp;

        if (opcionRespuesta.getIdRespuesta() != null) {
            rp = autoevaluacionRepository.findById(opcionRespuesta.getIdRespuesta())
                    .orElse(null);
        } else {
            if (opcionRespuesta.getIdRespuestaValoracion() == null) {
                if (cabeceraValoracion == null) {
                    String tipo = opcionRespuesta.getTipoFormulario();

                    Formulario fm = formularioRepository
                            .findByTipoFormularioNombre(tipo)
                            .orElse(null);

                    cabeceraValoracion = new Respuesta();
                    cabeceraValoracion.setEmprendimientos(emp);
                    cabeceraValoracion.setFormulario(fm);
                    cabeceraValoracion.setEsAutoEvaluacion(false);
                    cabeceraValoracion.setFechaRespuesta(LocalDateTime.now());

                    cabeceraValoracion = autoevaluacionRepository.save(cabeceraValoracion);
                }
                rp = cabeceraValoracion;
            } else {
                if (cabeceraAutoevaluacion == null) {
                    Respuesta valoracionOrigen = autoevaluacionRepository
                            .findById(opcionRespuesta.getIdRespuestaValoracion())
                            .orElse(null);

                    Formulario fmAuto = formularioRepository
                            .findByTipoFormularioNombre("AUTOEVALUACION")
                            .orElse(null);

                    cabeceraAutoevaluacion = new Respuesta();
                    cabeceraAutoevaluacion.setEmprendimientos(emp);
                    cabeceraAutoevaluacion.setFormulario(fmAuto);
                    cabeceraAutoevaluacion.setEsAutoEvaluacion(true);
                    cabeceraAutoevaluacion.setFechaRespuesta(LocalDateTime.now());
                    cabeceraAutoevaluacion.setRespuesta(valoracionOrigen);

                    cabeceraAutoevaluacion = autoevaluacionRepository.save(cabeceraAutoevaluacion);
                }
                rp = cabeceraAutoevaluacion;
            }
        }

        return new ResultadoCabeceras(rp, cabeceraValoracion, cabeceraAutoevaluacion);
    }

    public static class ResultadoCabeceras {
        private final Respuesta respuestaPrincipal;
        private final Respuesta cabeceraValoracion;
        private final Respuesta cabeceraAutoevaluacion;

        public ResultadoCabeceras(Respuesta respuestaPrincipal,
                                  Respuesta cabeceraValoracion,
                                  Respuesta cabeceraAutoevaluacion) {
            this.respuestaPrincipal = respuestaPrincipal;
            this.cabeceraValoracion = cabeceraValoracion;
            this.cabeceraAutoevaluacion = cabeceraAutoevaluacion;
        }

        public Respuesta getRespuestaPrincipal() {
            return respuestaPrincipal;
        }

        public Respuesta getCabeceraValoracion() {
            return cabeceraValoracion;
        }

        public Respuesta getCabeceraAutoevaluacion() {
            return cabeceraAutoevaluacion;
        }
    }
}
