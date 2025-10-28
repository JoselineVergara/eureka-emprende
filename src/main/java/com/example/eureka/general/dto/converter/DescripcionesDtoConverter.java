package com.example.eureka.general.dto.converter;

import com.example.eureka.general.dto.DescripcionesDTO;
import com.example.eureka.general.dto.OpcionesPersonaJuridicaDTO;
import com.example.eureka.model.Descripciones;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DescripcionesDtoConverter {

    public DescripcionesDTO convertirUsuarioXEmpresaModelToDto(Descripciones model) {
        if (model != null) {
            DescripcionesDTO dto = new DescripcionesDTO();
            dto.setId(model.getId());
            dto.setDescripcion(model.getDescripcion());
            dto.setEstado(model.getEsActivo());
            dto.setCantidadMaximaCaracteres(model.getCantidadMaximaCaracteres());

            return dto;
        }
        return null;
    }
}
