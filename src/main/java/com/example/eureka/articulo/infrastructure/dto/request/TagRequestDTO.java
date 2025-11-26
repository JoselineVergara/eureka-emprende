package com.example.eureka.articulo.infrastructure.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TagRequestDTO {

    @NotBlank(message = "El nombre del tag es obligatorio")
    private String nombre;
}