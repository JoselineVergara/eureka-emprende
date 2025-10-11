package com.example.eureka.entrepreneurship.service;

import com.example.eureka.entrepreneurship.dto.ArticuloRequestDTO;
import com.example.eureka.entrepreneurship.dto.ArticuloResponseDTO;
import com.example.eureka.entrepreneurship.dto.TagDTO;
import com.example.eureka.enums.EstadoArticulo;

import java.time.LocalDateTime;
import java.util.List;

public interface IBlogService {

    ArticuloResponseDTO crearArticulo(ArticuloRequestDTO request, Integer idUsuario);

    ArticuloResponseDTO editarArticulo(Integer idArticulo, ArticuloRequestDTO request, Integer idUsuario);

    List<ArticuloResponseDTO> obtenerArticulos(EstadoArticulo estado, LocalDateTime fechaInicio, LocalDateTime fechaFin);


    void archivarArticulo(Integer idArticulo, Integer idUsuario);
    void desarchivarArticulo(Integer idArticulo, Integer idUsuario);

    List<ArticuloResponseDTO> obtenerArticulosPorTag(Integer idTag);

    ArticuloResponseDTO obtenerArticuloPorId(Integer idArticulo);

    List<TagDTO> obtenerTodosTags();

    TagDTO crearTag(String nombre, Integer idUsuario);

    // NUEVO: Validar si usuario es administrador
    private void validarAdministrador(Integer idUsuario) {

    }
}