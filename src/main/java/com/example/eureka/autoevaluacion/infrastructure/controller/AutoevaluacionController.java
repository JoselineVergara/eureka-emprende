package com.example.eureka.autoevaluacion.infrastructure.controller;

import com.example.eureka.autoevaluacion.infrastructure.dto.ListadoAutoevaluacionDTO;
import com.example.eureka.autoevaluacion.infrastructure.dto.RespuestaResponseDTO;
import com.example.eureka.autoevaluacion.port.in.AutoevaluacionService;
import com.example.eureka.autoevaluacion.domain.model.Respuesta;
import com.example.eureka.formulario.infrastructure.dto.response.OpcionRespuestaDTO;
import com.example.eureka.shared.util.PageResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Slf4j
@RestController
@RequestMapping("/v1/autoevaluacion")
@RequiredArgsConstructor
public class AutoevaluacionController {

    private final AutoevaluacionService autoevaluacionService;

    @GetMapping("/{idAutoevaluacion}/detalle")
    public ResponseEntity<PageResponseDTO<OpcionRespuestaDTO>> obtenerDetalleAutoevaluacion(
            @PathVariable Long idAutoevaluacion,
            @org.springdoc.core.annotations.ParameterObject Pageable pageable) {

        Page<OpcionRespuestaDTO> page = autoevaluacionService.obtenerDetalleAutoevaluacion(idAutoevaluacion, pageable);
        return ResponseEntity.ok(PageResponseDTO.fromPage(page));
    }

    @GetMapping
    public ResponseEntity<PageResponseDTO<ListadoAutoevaluacionDTO>> listarAutoevaluaciones(
            @org.springdoc.core.annotations.ParameterObject Pageable pageable) {

        Page<ListadoAutoevaluacionDTO> page = autoevaluacionService.listarAutoevaluaciones(pageable);
        return ResponseEntity.ok(PageResponseDTO.fromPage(page));
    }

}
