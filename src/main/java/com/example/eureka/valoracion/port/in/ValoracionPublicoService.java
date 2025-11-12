package com.example.eureka.valoracion.port.in;

import com.example.eureka.valoracion.infrastructure.dto.publico.request.ValoracionRequestDTO;
import com.example.eureka.valoracion.infrastructure.dto.publico.response.ValoracionResponseDTO;

public interface ValoracionPublicoService {

    ValoracionResponseDTO registrarValoracion(ValoracionRequestDTO request);

    Double obtenerPromedioEmprendimiento(Integer idEmprendimiento);
}
