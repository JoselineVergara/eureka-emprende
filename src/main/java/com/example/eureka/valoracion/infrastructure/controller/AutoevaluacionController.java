package com.example.eureka.valoracion.infrastructure.controller;

import com.example.eureka.shared.util.PageResponseDTO;
import com.example.eureka.shared.util.SecurityUtils;
import com.example.eureka.valoracion.infrastructure.dto.emprendedor.request.AutoevaluacionRequestDTO;
import com.example.eureka.valoracion.infrastructure.dto.emprendedor.response.AutoevaluacionResponseDTO;
import com.example.eureka.valoracion.port.in.AutoevaluacionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/autoevaluaciones")
@RequiredArgsConstructor
public class AutoevaluacionController {

    private final AutoevaluacionService autoevaluacionService;
    private final SecurityUtils securityUtils;

    /**
     * API 1: Crear autoevaluación (solo EMPRENDEDOR)
     * POST /v1/autoevaluaciones
     */
    @PostMapping
    @PreAuthorize("hasRole('EMPRENDEDOR')")
    public ResponseEntity<AutoevaluacionResponseDTO> registrarAutoevaluacion(
            @Valid @RequestBody AutoevaluacionRequestDTO request) {

        Integer idUsuario = securityUtils.getIdUsuarioAutenticado();
        AutoevaluacionResponseDTO response =
                autoevaluacionService.registrarAutoevaluacion(request, idUsuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * API 2: Obtener todas las autoevaluaciones (solo ADMINISTRADOR)
     * GET /v1/autoevaluaciones/admin
     * GET /v1/autoevaluaciones/admin?idEmprendimiento=123 (filtro opcional)
     */
    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<PageResponseDTO<AutoevaluacionResponseDTO>> obtenerAutoevaluacionesAdmin(
            @RequestParam(required = false) Integer idEmprendimiento,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        PageResponseDTO<AutoevaluacionResponseDTO> autoevaluaciones =
                autoevaluacionService.obtenerAutoevaluacionesAdmin(idEmprendimiento, pageable);
        return ResponseEntity.ok(autoevaluaciones);
    }

    /**
     * API 3: Obtener autoevaluación por ID con todas las respuestas (solo ADMINISTRADOR)
     * GET /v1/autoevaluaciones/{id}
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<AutoevaluacionResponseDTO> obtenerAutoevaluacionPorId(
            @PathVariable Long id) {

        AutoevaluacionResponseDTO autoevaluacion =
                autoevaluacionService.obtenerAutoevaluacionPorId(id);
        return ResponseEntity.ok(autoevaluacion);
    }
}