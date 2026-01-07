package com.example.eureka.formulario.application.service;

import com.example.eureka.entrepreneurship.domain.model.Emprendimientos;
import com.example.eureka.entrepreneurship.domain.model.OpcionRespuesta;
import com.example.eureka.entrepreneurship.port.out.IEmprendimientosRepository;
import com.example.eureka.entrepreneurship.port.out.IPreguntaRepository;
import com.example.eureka.formulario.domain.model.Opciones;
import com.example.eureka.autoevaluacion.domain.model.Respuesta;
import com.example.eureka.formulario.domain.model.Pregunta;
import com.example.eureka.formulario.infrastructure.dto.response.OpcionResponseDTO;
import com.example.eureka.formulario.infrastructure.dto.response.OpcionRespuestaDTO;
import com.example.eureka.formulario.infrastructure.dto.response.OpcionRespuestaRequestDTO;
import com.example.eureka.formulario.port.in.OpcionRespuestaService;
import com.example.eureka.formulario.port.out.IOpcionRepository;
import com.example.eureka.formulario.port.out.IOpcionRespuestaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class OpcionRespuestaServiceImpl implements OpcionRespuestaService {

    private final IOpcionRespuestaRepository opcionRespuestaRepository;
    private final IEmprendimientosRepository emprendimientosRepository;
    private final IOpcionRepository opcionRepository;
    private final IPreguntaRepository preguntaRepository;
    private final RespuestaManager respuestaManager; // NUEVO
    private final EvaluacionEmprendimientoService evaluacionEmprendimientoService;


    public OpcionRespuestaServiceImpl(IOpcionRespuestaRepository opcionRespuestaRepository,
                                      IEmprendimientosRepository emprendimientosRepository,
                                      IOpcionRepository opcionRepository,
                                      IPreguntaRepository preguntaRepository,
                                      RespuestaManager respuestaManager,
                                      EvaluacionEmprendimientoService evaluacionEmprendimientoService) { // NUEVO
        this.opcionRespuestaRepository = opcionRespuestaRepository;
        this.emprendimientosRepository = emprendimientosRepository;
        this.opcionRepository = opcionRepository;
        this.preguntaRepository = preguntaRepository;
        this.respuestaManager = respuestaManager; // NUEVO
        this.evaluacionEmprendimientoService = evaluacionEmprendimientoService;
    }

    @Override
    public Page<OpcionRespuestaDTO> findAllByRespuesta(Respuesta respuesta, Pageable pageable) {
        List<OpcionRespuestaDTO> opcionRespuestaDTOs = new ArrayList<>();
        List<OpcionRespuesta> opcionRespuestas = opcionRespuestaRepository.findAllByRespuesta(respuesta);
        if(opcionRespuestas != null) {

            for(OpcionRespuesta opcionRespuesta : opcionRespuestas) {

                OpcionRespuestaDTO existente = opcionRespuestaDTOs.stream()
                        .filter(dto -> dto.getId().equals(opcionRespuesta.getId()))
                        .findFirst()
                        .orElse(null);

                if (existente != null) {
                    if (existente.getOpciones() == null) {
                        existente.setOpciones(new ArrayList<>());
                    }
                    OpcionResponseDTO op = new OpcionResponseDTO();
                    op.setIdOpcion(opcionRespuesta.getOpciones().getIdOpcion());
                    op.setOpcion(opcionRespuesta.getOpciones().getOpcion());
                    existente.getOpciones().add(op);
                    continue;
                }

                OpcionRespuestaDTO nuevo = new OpcionRespuestaDTO();
                nuevo.setId(opcionRespuesta.getId());

                List<OpcionResponseDTO> listaOpciones = new ArrayList<>();
                OpcionResponseDTO op = new OpcionResponseDTO();
                op.setIdOpcion(opcionRespuesta.getOpciones().getIdOpcion());
                op.setOpcion(opcionRespuesta.getOpciones().getOpcion());
                listaOpciones.add(op);
                nuevo.setOpciones(listaOpciones);

                nuevo.setIdRespuesta(opcionRespuesta.getRespuesta().getId());
                nuevo.setValorescala(opcionRespuesta.getValorescala());
                nuevo.setIdEmprendimientos(opcionRespuesta.getEmprendimiento() == null ? 0 : opcionRespuesta.getEmprendimiento().getId());
                nuevo.setIdPregunta(opcionRespuesta.getPregunta() == null ? 0 :  opcionRespuesta.getPregunta().getIdPregunta().intValue());
                nuevo.setPregunta(
                        opcionRespuesta.getPregunta() == null ? null : opcionRespuesta.getPregunta().getPregunta()
                );
                opcionRespuestaDTOs.add(nuevo);
            }

            return  new PageImpl<>(opcionRespuestaDTOs, pageable, opcionRespuestaDTOs.size());
        }
        return new PageImpl<>(List.of(), pageable, opcionRespuestaDTOs.size());
    }

    @Override
    @Transactional
    public List<OpcionRespuestaDTO> save(List<OpcionRespuestaRequestDTO> ls) {
        List<OpcionRespuestaDTO> opcionRespuestaDTOs = new ArrayList<>();
        List<OpcionRespuesta> respuestasGuardadas = new ArrayList<>();
        double sumaValores = 0;
        int contador = 0;

        Emprendimientos emp = null;
        Respuesta cabeceraValoracion = null;
        Respuesta cabeceraAutoevaluacion = null;

        for (OpcionRespuestaRequestDTO opcionRespuesta : ls) {

            if (emp == null) {
                emp = emprendimientosRepository.findById(opcionRespuesta.getIdEmprendimiento())
                        .orElse(null);
            }

            Pregunta p = preguntaRepository.findById(opcionRespuesta.getIdsPregunta().longValue())
                    .orElse(null);

            // AQUÍ ES DONDE ANTES TENÍAS TODO EL BLOQUE GIGANTE DE CABECERAS
            RespuestaManager.ResultadoCabeceras resultado = respuestaManager.obtenerRespuestaCabecera(
                    opcionRespuesta,
                    emp,
                    cabeceraValoracion,
                    cabeceraAutoevaluacion
            );

            Respuesta rp = resultado.getRespuestaPrincipal();
            cabeceraValoracion = resultado.getCabeceraValoracion();
            cabeceraAutoevaluacion = resultado.getCabeceraAutoevaluacion();

            List<OpcionResponseDTO> idsOpciones = new ArrayList<>();
            OpcionRespuesta op = null;

            if (opcionRespuesta.getIdsOpciones() != null && !opcionRespuesta.getIdsOpciones().isEmpty()) {
                for (Integer id : opcionRespuesta.getIdsOpciones()) {
                    op = new OpcionRespuesta();
                    Opciones opc = opcionRepository.findById(id.longValue());
                    op.setRespuesta(rp);
                    op.setOpciones(opc);
                    op.setEmprendimiento(emp);
                    op.setPregunta(p);
                    op.setValorescala(opcionRespuesta.getValorescala());
                    op = opcionRespuestaRepository.save(op);
                    respuestasGuardadas.add(op);

                    OpcionResponseDTO opr = new OpcionResponseDTO();
                    opr.setIdOpcion(op.getOpciones().getIdOpcion());
                    opr.setOpcion(op.getOpciones().getOpcion());
                    idsOpciones.add(opr);
                }
            } else {
                op = new OpcionRespuesta();
                op.setRespuesta(rp);
                op.setOpciones(null);
                op.setEmprendimiento(emp);
                op.setPregunta(p);
                op.setValorescala(opcionRespuesta.getValorescala());
                op = opcionRespuestaRepository.save(op);
                respuestasGuardadas.add(op);
            }

            OpcionRespuestaDTO opcionRespuestaDTO = new OpcionRespuestaDTO();
            opcionRespuestaDTO.setId(op.getId());
            opcionRespuestaDTO.setOpciones(idsOpciones);
            opcionRespuestaDTO.setIdRespuesta(rp.getId());
            opcionRespuestaDTO.setIdPregunta(p.getIdPregunta().intValue());
            opcionRespuestaDTO.setIdEmprendimientos(emp.getId());
            opcionRespuestaDTO.setValorescala(op.getValorescala());

            opcionRespuestaDTOs.add(opcionRespuestaDTO);

        }

        evaluacionEmprendimientoService.procesarEvaluacion(
                emp,
                respuestasGuardadas,
                cabeceraValoracion,
                ls
        );

        return opcionRespuestaDTOs;
    }

}
