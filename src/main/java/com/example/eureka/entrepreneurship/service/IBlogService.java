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

    // ← ACTUALIZADO: ahora incluye el parámetro idTag
    List<ArticuloResponseDTO> obtenerArticulos(EstadoArticulo estado, Integer idTag, LocalDateTime fechaInicio, LocalDateTime fechaFin);

    void archivarArticulo(Integer idArticulo, Integer idUsuario);

    void desarchivarArticulo(Integer idArticulo, Integer idUsuario);

    List<ArticuloResponseDTO> obtenerArticulosPorTag(Integer idTag);

    ArticuloResponseDTO obtenerArticuloPorId(Integer idArticulo);

    List<TagDTO> obtenerTodosTags();

    TagDTO crearTag(String nombre, Integer idUsuario);
}