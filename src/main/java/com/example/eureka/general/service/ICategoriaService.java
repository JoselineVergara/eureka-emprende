package com.example.eureka.general.service;

import com.example.eureka.general.dto.CategoriaRequestDTO;
import com.example.eureka.general.dto.CategoriasDTO;

import java.util.List;

public interface ICategoriaService {
    List<CategoriasDTO> listarCategoaria();
    CategoriasDTO obtenerCategoriaPorId(Integer id);
    CategoriasDTO crearCategoria(CategoriaRequestDTO dto);
    CategoriasDTO actualizarCategoaria(Integer id, CategoriaRequestDTO dto);
    void eliminarCategoria(Integer id);
}