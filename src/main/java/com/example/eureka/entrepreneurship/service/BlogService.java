package com.example.eureka.entrepreneurship.service;

import com.example.eureka.domain.enums.EstadoArticulo;
import com.example.eureka.entrepreneurship.dto.admin.ArticuloAdminDTO;
import com.example.eureka.entrepreneurship.dto.publico.ArticuloPublicoDTO;
import com.example.eureka.entrepreneurship.dto.request.ArticuloRequestDTO;
import com.example.eureka.entrepreneurship.dto.response.ArticuloResponseDTO;
import com.example.eureka.entrepreneurship.dto.shared.TagDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

public interface BlogService {

    ArticuloResponseDTO crearArticulo(ArticuloRequestDTO request, Integer idUsuario);

    ArticuloResponseDTO editarArticulo(Integer idArticulo, ArticuloRequestDTO request, Integer idUsuario);

    void archivarArticulo(Integer idArticulo, Integer idUsuario);

    void desarchivarArticulo(Integer idArticulo, Integer idUsuario);

    // Público - solo publicados
    Page<ArticuloPublicoDTO> obtenerArticulosPublicos(
            LocalDateTime fechaInicio,
            LocalDateTime fechaFin,
            Integer idTag,
            String titulo,
            Pageable pageable);

    // Admin - todos los estados
    Page<ArticuloAdminDTO> obtenerArticulosAdmin(
            EstadoArticulo estado,
            LocalDateTime fechaInicio,
            LocalDateTime fechaFin,
            Integer idTag,
            String titulo,
            Pageable pageable);

    ArticuloPublicoDTO obtenerArticuloPublicoPorId(Integer idArticulo);

    ArticuloResponseDTO obtenerArticuloPorId(Integer idArticulo);

    List<TagDTO> obtenerTodosTags();

    TagDTO crearTag(String nombre, Integer idUsuario);
}