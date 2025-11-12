package com.example.eureka.valoracion.port.in;

import com.example.eureka.shared.util.PageResponseDTO;
import com.example.eureka.valoracion.infrastructure.dto.emprendedor.request.AutoevaluacionRequestDTO;
import com.example.eureka.valoracion.infrastructure.dto.emprendedor.response.AutoevaluacionResponseDTO;
import org.springframework.data.domain.Pageable;

public interface AutoevaluacionService {

    AutoevaluacionResponseDTO registrarAutoevaluacion(
            AutoevaluacionRequestDTO request,
            Integer idUsuario
    );

    PageResponseDTO<AutoevaluacionResponseDTO> obtenerAutoevaluacionesAdmin(
            Integer idEmprendimiento,
            Pageable pageable
    );

    AutoevaluacionResponseDTO obtenerAutoevaluacionPorId(Long idRespuesta);
}