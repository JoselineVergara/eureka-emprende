package com.example.eureka.autoevaluacion.application.service;

import com.example.eureka.autoevaluacion.domain.service.ValoracionCalculator;
import com.example.eureka.autoevaluacion.infrastructure.dto.*;
import com.example.eureka.autoevaluacion.domain.model.Respuesta;
import com.example.eureka.autoevaluacion.port.out.IAutoevaluacionRepository;
import com.example.eureka.autoevaluacion.port.in.AutoevaluacionService;
import com.example.eureka.formulario.infrastructure.dto.response.OpcionRespuestaDTO;
import com.example.eureka.formulario.port.in.OpcionRespuestaService;
import com.example.eureka.shared.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AutoevaluacionServiceImpl implements AutoevaluacionService {

    private final IAutoevaluacionRepository valoracionRepository;
    private final OpcionRespuestaService opcionRespuestaService;
    private final ValoracionCalculator valoracionCalculator;


    public Page<ListadoAutoevaluacionDTO> listarAutoevaluaciones(Pageable pageable) {
        Page<Respuesta> page = valoracionRepository.findAllByEsAutoEvaluacionTrue(pageable);

        return page.map(r -> {
            ListadoAutoevaluacionDTO dto = new ListadoAutoevaluacionDTO();
            dto.setIdAutoevaluacion(r.getId());
            dto.setIdValoracionOrigen(r.getRespuesta() != null ? r.getRespuesta().getId() : null);
            dto.setEsAutoevaluacion(r.getEsAutoEvaluacion());
            dto.setFechaRespuesta(r.getFechaRespuesta());
            dto.setIdFormulario(r.getFormulario().getIdFormulario().intValue());
            dto.setFormulario(r.getFormulario().getNombre());
            dto.setIdEmprendimiento(r.getEmprendimientos().getId());
            dto.setEmprendimiento(r.getEmprendimientos().getNombreComercial());

            // NUEVO: Agregar datos de la valoración origen
            if (r.getRespuesta() != null) {
                ValoracionResumenDTO valoracion = new ValoracionResumenDTO();
                valoracion.setIdValoracion(r.getRespuesta().getId());
                valoracion.setFechaValoracion(r.getRespuesta().getFechaRespuesta());
                valoracion.setTipoFormulario(r.getRespuesta().getFormulario().getTipoFormulario().getNombre());

                // Calcular promedio de la valoración
                Double promedio = valoracionCalculator.calcularPromedioValoracion(r.getRespuesta().getId());
                valoracion.setPromedio(promedio);

                dto.setValoracionOrigen(valoracion);
            }

            return dto;
        });
    }

    public Page<OpcionRespuestaDTO> obtenerDetalleAutoevaluacion(Long idAutoevaluacion, Pageable pageable) {
        Respuesta autoeval = valoracionRepository.findById(idAutoevaluacion.intValue())
                .orElseThrow(() -> new BusinessException("Autoevaluación no encontrada"));

        return opcionRespuestaService.findAllByRespuesta(autoeval, pageable);
    }



    @Override
    public Respuesta findById(Long idRespuesta) {
        return valoracionRepository.findById(idRespuesta.intValue()).orElse(null);
    }

}
