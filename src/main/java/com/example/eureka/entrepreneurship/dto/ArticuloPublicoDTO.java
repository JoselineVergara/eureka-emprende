package com.example.eureka.entrepreneurship.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArticuloPublicoDTO {
    private Integer idArticulo;
    private String titulo;
    private String descripcionCorta;
    private Integer idImagen;
    private String urlImagen;
    private LocalDateTime fechaCreacion;
    private List<TagDTO> tags;
}