package com.example.eureka.entrepreneurship.dto.shared;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmprendimientoSimpleDTO {

    private Long id;
    private String nombreComercial;
    private String ciudad;
}