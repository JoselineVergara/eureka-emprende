package com.example.eureka.formulario.application.service;

import com.example.eureka.autoevaluacion.port.out.IAutoevaluacionRepository;
import com.example.eureka.entrepreneurship.domain.model.Emprendimientos;
import com.example.eureka.entrepreneurship.domain.model.OpcionRespuesta;
import com.example.eureka.entrepreneurship.port.out.IEmprendimientosRepository;
import com.example.eureka.formulario.domain.model.Opciones;
import com.example.eureka.autoevaluacion.domain.model.Respuesta;
import com.example.eureka.formulario.infrastructure.dto.response.OpcionRespuestaDTO;
import com.example.eureka.formulario.infrastructure.dto.response.OpcionRespuestaResponseDTO;
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
    private final IEmprendimientosRepository  emprendimientosRepository;
    private final IOpcionRepository  opcionRepository;
    private final IAutoevaluacionRepository   autoevaluacionRepository;

    public OpcionRespuestaServiceImpl(IOpcionRespuestaRepository opcionRespuestaRepository, IEmprendimientosRepository emprendimientosRepository, IOpcionRepository opcionRepository, IAutoevaluacionRepository autoevaluacionRepository) {
        this.opcionRespuestaRepository = opcionRespuestaRepository;
        this.emprendimientosRepository = emprendimientosRepository;
        this.opcionRepository = opcionRepository;
        this.autoevaluacionRepository = autoevaluacionRepository;
    }

    @Override
    public Page<OpcionRespuestaDTO> findAllByRespuesta(Respuesta respuesta, Pageable pageable) {
        List<OpcionRespuestaDTO> opcionRespuestaDTOs = new ArrayList<>();
        List<OpcionRespuesta> opcionRespuestas = opcionRespuestaRepository.findAllByRespuesta(respuesta);
        if(opcionRespuestas != null) {
            for(OpcionRespuesta opcionRespuesta : opcionRespuestas) {
                OpcionRespuestaDTO opcionRespuestaDTO = new OpcionRespuestaDTO();
                opcionRespuestaDTO.setId(opcionRespuesta.getId());
                opcionRespuestaDTO.setOpciones(opcionRespuesta.getOpciones());
                opcionRespuestaDTO.setRespuesta(opcionRespuesta.getRespuesta());
                opcionRespuestaDTO.setValorescala(opcionRespuesta.getValorescala());
                opcionRespuestaDTO.setEmprendimientos(opcionRespuesta.getEmprendimiento());
                opcionRespuestaDTOs.add(opcionRespuestaDTO);
            }
            return  new PageImpl<>(opcionRespuestaDTOs, pageable, opcionRespuestaDTOs.size());
        }
        return new PageImpl<>(List.of(), pageable, opcionRespuestaDTOs.size());
    }

    @Override
    public Page<OpcionRespuestaDTO> findAllByOpciones(Opciones opciones, Pageable pageable) {
        List<OpcionRespuestaDTO> opcionRespuestaDTOs = new ArrayList<>();
        List<OpcionRespuesta> opcionRespuestas = opcionRespuestaRepository.findAllByOpciones(opciones);
        if(opcionRespuestas != null) {
            for(OpcionRespuesta opcionRespuesta : opcionRespuestas) {
                OpcionRespuestaDTO opcionRespuestaDTO = new OpcionRespuestaDTO();
                opcionRespuestaDTO.setId(opcionRespuesta.getId());
                opcionRespuestaDTO.setOpciones(opcionRespuesta.getOpciones());
                opcionRespuestaDTO.setRespuesta(opcionRespuesta.getRespuesta());
                opcionRespuestaDTO.setValorescala(opcionRespuesta.getValorescala());
                opcionRespuestaDTO.setEmprendimientos(opcionRespuesta.getEmprendimiento());
                opcionRespuestaDTOs.add(opcionRespuestaDTO);
            }
            return  new PageImpl<>(opcionRespuestaDTOs, pageable, opcionRespuestaDTOs.size());

        }
        return new PageImpl<>(List.of(), pageable, opcionRespuestaDTOs.size());
    }

    @Override
    public List<OpcionRespuestaDTO> save(List<OpcionRespuestaResponseDTO> ls) {
        List<OpcionRespuestaDTO> opcionRespuestaDTOs = new ArrayList<>();

        for(OpcionRespuestaResponseDTO opcionRespuesta : ls){
            Emprendimientos emp = emprendimientosRepository.findById(opcionRespuesta.getIdEmprendimiento()).orElse(null);
            Opciones opc = opcionRepository.findById(opcionRespuesta.getIdOpcion().longValue());
            Respuesta rp = autoevaluacionRepository.findById(opcionRespuesta.getIdRespuesta()).orElse(null);
            OpcionRespuesta op = new  OpcionRespuesta();
            op.setRespuesta(rp);
            op.setOpciones(opc);
            op.setEmprendimiento(emp);
            op.setValorescala(opcionRespuesta.getValorescala());
            op = opcionRespuestaRepository.save(op);

            OpcionRespuestaDTO  opcionRespuestaDTO = new OpcionRespuestaDTO();
            opcionRespuestaDTO.setId(op.getId());
            opcionRespuestaDTO.setOpciones(op.getOpciones());
            opcionRespuestaDTO.setRespuesta(op.getRespuesta());
            opcionRespuestaDTO.setEmprendimientos(op.getEmprendimiento());
            opcionRespuestaDTO.setValorescala(op.getValorescala());

            opcionRespuestaDTOs.add(opcionRespuestaDTO);
        }


        return opcionRespuestaDTOs;
    }
}
