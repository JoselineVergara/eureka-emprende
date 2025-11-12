package com.example.eureka.valoracion.application.service.publico;

import com.example.eureka.domain.model.Emprendimientos;
import com.example.eureka.entrepreneurship.repository.IEmprendimientosRepository;
import com.example.eureka.formulario.domain.model.Formulario;
import com.example.eureka.formulario.domain.model.FormularioPregunta;
import com.example.eureka.formulario.port.out.IFormularioRepository;
import com.example.eureka.notificacion.port.in.NotificacionService;
import com.example.eureka.shared.exception.BusinessException;
import com.example.eureka.valoracion.domain.model.OpcionRespuesta;
import com.example.eureka.valoracion.domain.model.Respuesta;
import com.example.eureka.valoracion.infrastructure.dto.publico.request.ValoracionRequestDTO;
import com.example.eureka.valoracion.infrastructure.dto.publico.response.ValoracionResponseDTO;
import com.example.eureka.valoracion.port.in.ValoracionPublicoService;
import com.example.eureka.valoracion.port.out.IOpcionRespuestaRepository;
import com.example.eureka.valoracion.port.out.IRespuestaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ValoracionPublicoServiceImpl implements ValoracionPublicoService {

    private final IRespuestaRepository respuestaRepository;
    private final IOpcionRespuestaRepository opcionRespuestaRepository;
    private final IEmprendimientosRepository emprendimientosRepository;
    private final IFormularioRepository formularioRepository;
    private final NotificacionService notificacionService;

    private static final double UMBRAL_AUTOEVALUACION = 2.0;

    @Override
    @Transactional
    public ValoracionResponseDTO registrarValoracion(ValoracionRequestDTO request) {

        // Validar emprendimiento
        Emprendimientos emprendimiento = emprendimientosRepository.findById(request.getIdEmprendimiento())
                .orElseThrow(() -> new BusinessException(
                        "Emprendimiento no encontrado con ID: " + request.getIdEmprendimiento()));

        // Validar formulario
        Formulario formulario = formularioRepository.findById(request.getIdFormulario())
                .orElseThrow(() -> new BusinessException(
                        "Formulario no encontrado con ID: " + request.getIdFormulario()));

        // Validar que el formulario sea de tipo EVALUACION_SERVICIO o EVALUACION_PRODUCTO
        String tipoFormulario = formulario.getTipoFormulario().getNombre();
        if (!"EVALUACION_SERVICIO".equals(tipoFormulario) && !"EVALUACION_PRODUCTO".equals(tipoFormulario)) {
            throw new BusinessException("El formulario debe ser de tipo EVALUACION_SERVICIO o EVALUACION_PRODUCTO");
        }

        // Validar que el formulario esté activo
        if (!"ACTIVO".equals(formulario.getEstado())) {
            throw new BusinessException("El formulario no está activo");
        }

        // Crear respuesta principal
        Respuesta respuesta = new Respuesta();
        respuesta.setIdFormulario(request.getIdFormulario());
        respuesta.setIdEmprendimiento(request.getIdEmprendimiento());
        respuesta.setFechaRespuesta(LocalDateTime.now());
        respuesta.setEsAutoevaluacion(false);
        respuesta.setIdValoracionOrigen(null);

        Respuesta respuestaGuardada = respuestaRepository.save(respuesta);

        // Obtener preguntas del formulario
        Map<Long, FormularioPregunta> preguntasMap = formulario.getFormularioPreguntas()
                .stream()
                .collect(Collectors.toMap(
                        fp -> fp.getPregunta().getIdPregunta(),
                        fp -> fp
                ));

        List<OpcionRespuesta> opcionesRespuesta = new ArrayList<>();
        double sumaValores = 0;
        int contadorRespuestas = 0;

        for (ValoracionRequestDTO.RespuestaPreguntaDTO respuestaDTO : request.getRespuestas()) {
            FormularioPregunta formularioPregunta = preguntasMap.get(respuestaDTO.getIdPregunta());

            if (formularioPregunta == null) {
                throw new BusinessException(
                        "Pregunta no encontrada con ID: " + respuestaDTO.getIdPregunta());
            }

            String tipoPregunta = formularioPregunta.getPregunta().getTipo();

            if (!"ESCALA".equals(tipoPregunta)) {
                throw new BusinessException(
                        "La pregunta " + respuestaDTO.getIdPregunta() + " debe ser de tipo ESCALA para valoraciones");
            }

            if (respuestaDTO.getValorEscala() == null) {
                throw new BusinessException(
                        "El valor de escala es obligatorio para la pregunta: " + respuestaDTO.getIdPregunta());
            }

            // Validar que el valor esté dentro del rango permitido
            int numeroRespuestas = formularioPregunta.getPregunta().getNumeroRespuestas();
            if (respuestaDTO.getValorEscala() < 1 || respuestaDTO.getValorEscala() > numeroRespuestas) {
                throw new BusinessException(
                        "El valor debe estar entre 1 y " + numeroRespuestas + " para la pregunta: " +
                                respuestaDTO.getIdPregunta());
            }

            OpcionRespuesta opcionRespuesta = new OpcionRespuesta(
                    respuestaGuardada.getIdRespuesta(),
                    respuestaDTO.getValorEscala()
            );
            opcionesRespuesta.add(opcionRespuesta);

            sumaValores += respuestaDTO.getValorEscala();
            contadorRespuestas++;
        }

        opcionRespuestaRepository.saveAll(opcionesRespuesta);

        // Calcular promedio
        double promedioObtenido = contadorRespuestas > 0 ? sumaValores / contadorRespuestas : 0;
        boolean requiereAutoevaluacion = promedioObtenido < UMBRAL_AUTOEVALUACION;

        // Enviar notificación si requiere autoevaluación
        if (requiereAutoevaluacion) {
            notificacionService.crearNotificacionAutoevaluacion(
                    emprendimiento.getIdUsuario(),
                    emprendimiento.getIdEmprendimiento(),
                    emprendimiento.getNombre(),
                    respuestaGuardada.getIdRespuesta(),
                    promedioObtenido
            );
        }

        return ValoracionResponseDTO.builder()
                .idRespuesta(respuestaGuardada.getIdRespuesta())
                .mensaje("Valoración registrada exitosamente")
                .promedioObtenido(promedioObtenido)
                .requiereAutoevaluacion(requiereAutoevaluacion)
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public Double obtenerPromedioEmprendimiento(Integer idEmprendimiento) {

        Emprendimientos emprendimiento = emprendimientosRepository.findById(idEmprendimiento)
                .orElseThrow(() -> new BusinessException(
                        "Emprendimiento no encontrado con ID: " + idEmprendimiento));

        Double promedio = respuestaRepository.calcularPromedioValoracion(idEmprendimiento);

        return promedio != null ? promedio : 0.0;
    }
}