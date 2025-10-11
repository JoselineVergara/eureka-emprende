package com.example.eureka.entrepreneurship.dto;

import com.example.eureka.enums.EstadoArticulo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticuloRequestDTO {

    @NotBlank(message = "El título es obligatorio")
    private String titulo;

    private String descripcionCorta;

    @NotBlank(message = "El contenido es obligatorio")
    private String contenido;

    @NotNull(message = "El ID de imagen es obligatorio")
    private Integer idImagen;

    private List<Integer> idsTags; // IDs de tags existentes
    private List<String> nombresTags; // Nombres de tags nuevos

    private EstadoArticulo estado; // NUEVO: estado inicial (BORRADOR o PUBLICADO)
}