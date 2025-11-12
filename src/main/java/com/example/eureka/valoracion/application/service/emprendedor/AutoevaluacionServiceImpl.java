package com.example.eureka.valoracion.application.service.emprendedor;

import com.example.eureka.domain.model.Emprendimientos;
import com.example.eureka.entrepreneurship.repository.IEmprendimientosRepository;
import com.example.eureka.formulario.domain.model.Formulario;
import com.example.eureka.formulario.domain.model.Pregunta;
import com.example.eureka.formulario.port.out.IFormularioRepository;
import com.example.eureka.formulario.port.out.IPreguntaRepository;
import com.example.eureka.shared.exception.ResourceNotFoundException;
import com.example.eureka.shared.util.PageResponseDTO;
import com.example.eureka.valoracion.domain.model.OpcionRespuesta;
import com.example.eureka.valoracion.domain.model.Respuesta;
import com.example.eureka.valoracion.infrastructure.dto.emprendedor.request.AutoevaluacionRequestDTO;
import com.example.eureka.valoracion.infrastructure.dto.emprendedor.response.AutoevaluacionResponseDTO;
import com.example.eureka.valoracion.infrastructure.dto.emprendedor.response.OpcionSeleccionadaDTO;
import com.example.eureka.valoracion.infrastructure.dto.emprendedor.response.PreguntaRespuestaDTO;
import com.example.eureka.valoracion.port.in.AutoevaluacionService;
import com.example.eureka.valoracion.port.out.IOpcionRespuestaRepository;
import com.example.eureka.valoracion.port.out.IRespuestaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AutoevaluacionServiceImpl implements AutoevaluacionService {

    private final IRespuestaRepository respuestaRepository;
    private final IOpcionRespuestaRepository opcionRespuestaRepository;
    private final IEmprendimientosRepository emprendimientosRepository;
    private final IFormularioRepository formularioRepository;
    private final IPreguntaRepository preguntaRepository;
    private final IOpcionRepository opcionRepository;

    @Override
    @Transactional
    public AutoevaluacionResponseDTO registrarAutoevaluacion(
            AutoevaluacionRequestDTO request,
            Integer idUsuario) {

        // Validar que la valoración de origen existe
        Respuesta valoracionOrigen = respuestaRepository.findById(request.getIdValoracionOrigen())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Valoración origen no encontrada con ID: " + request.getIdValoracionOrigen()));

        // Validar que no se haya creado ya una autoevaluación para esta valoración
        if (respuestaRepository.existeAutoevaluacionParaValoracion(request.getIdValoracionOrigen())) {
            throw new IllegalArgumentException(
                    "Ya existe una autoevaluación para esta valoración");
        }

        // Validar emprendimiento
        Emprendimientos emprendimiento = emprendimientosRepository.findById(request.getIdEmprendimiento())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Emprendimiento no encontrado con ID: " + request.getIdEmprendimiento()));

        // Validar que el emprendimiento pertenece al usuario
        if (!emprendimiento.getUsuarios().getId().equals(idUsuario)) {
            throw new IllegalArgumentException(
                    "El emprendimiento no pertenece al usuario autenticado");
        }

        // Validar formulario de autoevaluación
        Formulario formulario = formularioRepository.findById(request.getIdFormulario())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Formulario no encontrado con ID: " + request.getIdFormulario()));

        if (!"AUTOEVALUACION".equals(formulario.getTipoFormulario())) {
            throw new IllegalArgumentException("El formulario debe ser de tipo AUTOEVALUACION");
        }

        // Crear respuesta de autoevaluación
        Respuesta autoevaluacion = new Respuesta();
        autoevaluacion.setIdFormulario(request.getIdFormulario());
        autoevaluacion.setIdEmprendimiento(request.getIdEmprendimiento());
        autoevaluacion.setFechaRespuesta(LocalDateTime.now());
        autoevaluacion.setEsAutoevaluacion(true);
        autoevaluacion.setIdValoracionOrigen(request.getIdValoracionOrigen());

        Respuesta autoevaluacionGuardada = respuestaRepository.save(autoevaluacion);

        // Procesar respuestas
        Map<Long, Pregunta> preguntasMap = preguntaRepository.findByFormularioId(request.getIdFormulario())
                .stream()
                .collect(Collectors.toMap(Pregunta::getIdPregunta, p -> p));

        List<OpcionRespuesta> opcionesRespuesta = new ArrayList<>();

        for (AutoevaluacionRequestDTO.RespuestaPreguntaDTO respuestaDTO : request.getRespuestas()) {
            Pregunta pregunta = preguntasMap.get(respuestaDTO.getIdPregunta());

            if (pregunta == null) {
                throw new ResourceNotFoundException(
                        "Pregunta no encontrada con ID: " + respuestaDTO.getIdPregunta());
            }

            switch (pregunta.getTipo()) {
                case "OPCION_MULTIPLE":
                    if (respuestaDTO.getIdsOpciones() == null || respuestaDTO.getIdsOpciones().isEmpty()) {
                        throw new IllegalArgumentException(
                                "Debe seleccionar al menos una opción para la pregunta: " + pregunta.getIdPregunta());
                    }
                    for (Long idOpcion : respuestaDTO.getIdsOpciones()) {
                        opcionesRespuesta.add(new OpcionRespuesta(
                                autoevaluacionGuardada.getIdRespuesta(),
                                idOpcion
                        ));
                    }
                    break;

                case "OPCION_UNICA":
                    if (respuestaDTO.getIdsOpciones() == null || respuestaDTO.getIdsOpciones().isEmpty()) {
                        throw new IllegalArgumentException(
                                "Debe seleccionar una opción para la pregunta: " + pregunta.getIdPregunta());
                    }
                    if (respuestaDTO.getIdsOpciones().size() > 1) {
                        throw new IllegalArgumentException(
                                "Solo puede seleccionar una opción para la pregunta: " + pregunta.getIdPregunta());
                    }
                    opcionesRespuesta.add(new OpcionRespuesta(
                            autoevaluacionGuardada.getIdRespuesta(),
                            respuestaDTO.getIdsOpciones().get(0)
                    ));
                    break;

                case "ESCALA":
                    if (respuestaDTO.getValorEscala() == null) {
                        throw new IllegalArgumentException(
                                "El valor de escala es obligatorio para la pregunta: " + pregunta.getIdPregunta());
                    }
                    opcionesRespuesta.add(new OpcionRespuesta(
                            autoevaluacionGuardada.getIdRespuesta(),
                            respuestaDTO.getValorEscala()
                    ));
                    break;

                default:
                    throw new IllegalArgumentException("Tipo de pregunta no soportado: " + pregunta.getTipo());
            }
        }

        opcionRespuestaRepository.saveAll(opcionesRespuesta);

        // Calcular promedio de la valoración original
        Double promedioOriginal = respuestaRepository.calcularPromedioDeValoracion(
                request.getIdValoracionOrigen());

        return construirAutoevaluacionResponse(
                autoevaluacionGuardada,
                emprendimiento.getNombreComercial(),
                promedioOriginal
        );
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponseDTO<AutoevaluacionResponseDTO> obtenerAutoevaluacionesAdmin(
            Integer idEmprendimiento,
            Pageable pageable) {

        Page<Respuesta> autoevaluaciones;

        if (idEmprendimiento != null) {
            // Validar que el emprendimiento existe
            emprendimientosRepository.findById(idEmprendimiento)
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Emprendimiento no encontrado con ID: " + idEmprendimiento));

            autoevaluaciones = respuestaRepository.findAutoevaluacionesByEmprendimiento(
                    idEmprendimiento,
                    pageable
            );
        } else {
            autoevaluaciones = respuestaRepository.findAllAutoevaluaciones(pageable);
        }

        List<AutoevaluacionResponseDTO> content = autoevaluaciones.getContent().stream()
                .map(this::construirAutoevaluacionResponseSimple)
                .collect(Collectors.toList());

        return PageResponseDTO.<AutoevaluacionResponseDTO>builder()
                .content(content)
                .pageNumber(autoevaluaciones.getNumber())
                .pageSize(autoevaluaciones.getSize())
                .totalElements(autoevaluaciones.getTotalElements())
                .totalPages(autoevaluaciones.getTotalPages())
                .last(autoevaluaciones.isLast())
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public AutoevaluacionResponseDTO obtenerAutoevaluacionPorId(Long idRespuesta) {

        Respuesta autoevaluacion = respuestaRepository.findById(idRespuesta)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Autoevaluación no encontrada con ID: " + idRespuesta));

        if (!autoevaluacion.getEsAutoevaluacion()) {
            throw new IllegalArgumentException(
                    "La respuesta " + idRespuesta + " no es una autoevaluación");
        }

        Emprendimientos emprendimiento = emprendimientosRepository.findById(autoevaluacion.getIdEmprendimiento())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Emprendimiento no encontrado con ID: " + autoevaluacion.getIdEmprendimiento()));

        Double promedioOriginal = null;
        if (autoevaluacion.getIdValoracionOrigen() != null) {
            promedioOriginal = respuestaRepository.calcularPromedioDeValoracion(
                    autoevaluacion.getIdValoracionOrigen());
        }

        return construirAutoevaluacionResponse(
                autoevaluacion,
                emprendimiento.getNombreComercial(),
                promedioOriginal
        );
    }

    // Métodos auxiliares

    private AutoevaluacionResponseDTO construirAutoevaluacionResponseSimple(Respuesta autoevaluacion) {
        Emprendimientos emprendimiento = emprendimientosRepository.findById(autoevaluacion.getIdEmprendimiento())
                .orElse(null);

        String nombreEmprendimiento = emprendimiento != null ? emprendimiento.getNombreComercial() : "Desconocido";

        Double promedioOriginal = null;
        if (autoevaluacion.getIdValoracionOrigen() != null) {
            promedioOriginal = respuestaRepository.calcularPromedioDeValoracion(
                    autoevaluacion.getIdValoracionOrigen());
        }

        return AutoevaluacionResponseDTO.builder()
                .idRespuesta(autoevaluacion.getIdRespuesta())
                .idEmprendimiento(autoevaluacion.getIdEmprendimiento())
                .nombreEmprendimiento(nombreEmprendimiento)
                .fechaRespuesta(autoevaluacion.getFechaRespuesta())
                .idValoracionOrigen(autoevaluacion.getIdValoracionOrigen())
                .promedioValoracionOriginal(promedioOriginal)
                .preguntasRespuestas(null) // No incluimos detalles en listados
                .build();
    }

    private AutoevaluacionResponseDTO construirAutoevaluacionResponse(
            Respuesta autoevaluacion,
            String nombreEmprendimiento,
            Double promedioOriginal) {

        // Obtener las opciones de respuesta con sus detalles
        List<OpcionRespuesta> opcionesRespuesta = opcionRespuestaRepository
                .findByRespuestaId(autoevaluacion.getIdRespuesta());

        // Agrupar por pregunta
        Map<Long, List<OpcionRespuesta>> respuestasPorPregunta = opcionesRespuesta.stream()
                .collect(Collectors.groupingBy(or ->
                        or.getOpcion() != null ? or.getOpcion().getPregunta().getIdPregunta() : -1L
                ));

        // Obtener todas las preguntas del formulario
        List<Pregunta> preguntas = preguntaRepository.findByFormularioId(autoevaluacion.getIdFormulario());

        List<PreguntaRespuestaDTO> preguntasRespuestas = new ArrayList<>();

        for (Pregunta pregunta : preguntas) {
            List<OpcionRespuesta> respuestasPreg = respuestasPorPregunta.getOrDefault(
                    pregunta.getIdPregunta(),
                    new ArrayList<>()
            );

            PreguntaRespuestaDTO preguntaDTO = PreguntaRespuestaDTO.builder()
                    .idPregunta(pregunta.getIdPregunta())
                    .textoPregunta(pregunta.getPregunta())
                    .tipoPregunta(pregunta.getTipo())
                    .build();

            if ("ESCALA".equals(pregunta.getTipo())) {
                // Para preguntas de escala
                Integer valorEscala = respuestasPreg.stream()
                        .map(OpcionRespuesta::getValorEscala)
                        .findFirst()
                        .orElse(null);
                preguntaDTO.setValorEscala(valorEscala);
            } else {
                // Para preguntas de opción
                List<OpcionSeleccionadaDTO> opciones = respuestasPreg.stream()
                        .filter(or -> or.getOpcion() != null)
                        .map(or -> OpcionSeleccionadaDTO.builder()
                                .idOpcion(or.getOpcion().getIdOpcion())
                                .textoOpcion(or.getOpcion().getOpcion())
                                .build())
                        .collect(Collectors.toList());
                preguntaDTO.setOpcionesSeleccionadas(opciones);
            }

            preguntasRespuestas.add(preguntaDTO);
        }

        return AutoevaluacionResponseDTO.builder()
                .idRespuesta(autoevaluacion.getIdRespuesta())
                .idEmprendimiento(autoevaluacion.getIdEmprendimiento())
                .nombreEmprendimiento(nombreEmprendimiento)
                .fechaRespuesta(autoevaluacion.getFechaRespuesta())
                .idValoracionOrigen(autoevaluacion.getIdValoracionOrigen())
                .promedioValoracionOriginal(promedioOriginal)
                .preguntasRespuestas(preguntasRespuestas)
                .build();
    }
}