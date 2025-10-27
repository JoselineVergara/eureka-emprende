package com.example.eureka.general.dto.converter;

import com.example.eureka.general.dto.CategoriasDTO;
import com.example.eureka.model.Categorias;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CategoriaDtoConverter {

    public CategoriasDTO convertirCategoriaModelToDto(Categorias model) {
        if (model != null) {
            CategoriasDTO dto = new CategoriasDTO();
            dto.setId(model.getId());
            dto.setNombre(model.getNombre());
            dto.setDescripcion(model.getDescripcion());
            if (model.getMultimedia() != null) {
                dto.setIdMultimedia(model.getMultimedia().getId());
                dto.setUrlImagen(model.getMultimedia().getUrlArchivo());
            }
            return dto;
        }
        return null;
    }
}
