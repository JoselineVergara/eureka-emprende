package com.example.eureka.valoracion.infrastructure.controller;

import com.example.eureka.valoracion.infrastructure.dto.publico.request.ValoracionRequestDTO;
import com.example.eureka.valoracion.infrastructure.dto.publico.response.ValoracionResponseDTO;
import com.example.eureka.valoracion.port.in.ValoracionPublicoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/valoraciones")
@RequiredArgsConstructor
public class ValoracionPublicoController {

    private final ValoracionPublicoService valoracionService;

    @PostMapping
    public ResponseEntity<ValoracionResponseDTO> registrarValoracion(
            @RequestBody ValoracionRequestDTO request) {
        ValoracionResponseDTO response = valoracionService.registrarValoracion(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/emprendimiento/{idEmprendimiento}/promedio")
    public ResponseEntity<Double> obtenerPromedio(
            @PathVariable Integer idEmprendimiento) {
        Double promedio = valoracionService.obtenerPromedioEmprendimiento(idEmprendimiento);
        return ResponseEntity.ok(promedio);
    }
}