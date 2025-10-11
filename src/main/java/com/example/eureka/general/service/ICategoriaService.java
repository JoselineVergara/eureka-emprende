package com.example.eureka.general.service;

import com.example.eureka.general.dto.CategoriasDTO;

import java.util.List;

public interface ICategoriaService {

    Integer crearCategoria(CategoriasDTO categoriasDTO);

    Integer actualizarCategoria(Integer id, CategoriasDTO categoriasDTO);

    void eliminarCategoria(Integer id);

    List<CategoriasDTO> obtenerCategorias();

    CategoriasDTO obtenerCategoriaPorId(Integer id);
}