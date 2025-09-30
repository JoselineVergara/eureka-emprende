package com.example.eureka.general.controller;

import com.example.eureka.general.dto.CategoriasDTO;
import com.example.eureka.general.dto.MultimediaDTO;
import com.example.eureka.general.repository.ICategoriasRepository;
import com.example.eureka.model.Categorias;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/v1/categorias")
@RequiredArgsConstructor
public class CategoriasController {

    private final ICategoriasRepository categoriasRepository;

    @GetMapping
    public ResponseEntity<?> obtenerCategorias() {
        List<Categorias> categorias = categoriasRepository.findAll();

        List<CategoriasDTO> categoriasDTOList = categorias.stream().map(categoria -> {
            CategoriasDTO dto = new CategoriasDTO();
            dto.setNombre(categoria.getNombre());
            dto.setDescripcion(categoria.getDescripcion());
            dto.setUrlImagen(categoria.getMultimedia().getUrlArchivo());
            return dto;
        }).toList();

        return ResponseEntity.ok(categoriasDTOList);
    }


}
