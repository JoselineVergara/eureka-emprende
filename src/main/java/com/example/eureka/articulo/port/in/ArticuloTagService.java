package com.example.eureka.articulo.port.in;

import com.example.eureka.articulo.domain.model.ArticulosBlog;
import com.example.eureka.articulo.infrastructure.dto.response.TagDTO;

import java.util.List;

public interface ArticuloTagService {

    void procesarTags(ArticulosBlog articulo, List<Integer> idsTags, List<String> nombresTags);

    List<TagDTO> obtenerTodosTags();

    TagDTO crearTag(String nombre, Integer idUsuario);
}
