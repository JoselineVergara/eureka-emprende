package com.example.eureka.entrepreneurship.dto;

import com.example.eureka.enums.EstadoArticulo;
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
public class ArticuloAdminDTO {
    private Integer idArticulo;
    private String titulo;
    private String descripcionCorta;
    private Integer idImagen;
    private String urlImagen;
    private LocalDateTime fechaCreacion;
    private EstadoArticulo estado;
    private LocalDateTime fechaModificacion;
    private Integer idUsuario;
    private String nombreUsuario;
    private List<TagDTO> tags;
}