package com.example.eureka.entrepreneurship.dto.shared;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmprendimientoPorCategoriaDTO {

    private Integer categoriaId;
    private String nombreCategoria;
    private List<EmprendimientoSimpleDTO> emprendimientos;
}