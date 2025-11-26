package com.example.eureka.formulario.port.in;

import com.example.eureka.formulario.infrastructure.dto.response.FormularioResponseDTO;

public interface FormularioService {

    FormularioResponseDTO getFormularioByTipo(String tipoFormulario);

    FormularioResponseDTO getFormularioById(Long id);
}