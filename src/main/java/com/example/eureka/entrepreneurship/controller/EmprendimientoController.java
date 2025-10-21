package com.example.eureka.entrepreneurship.controller;

import com.example.eureka.entrepreneurship.dto.*;
import com.example.eureka.entrepreneurship.service.IEmprendimientoService;
import com.example.eureka.model.SolicitudAprobacion;
import com.example.eureka.model.Usuarios;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/emprendimientos")
@RequiredArgsConstructor
public class EmprendimientoController {

    private final IEmprendimientoService emprendimientoService;

    /**
     * Crear estructura de emprendimiento (BORRADOR o enviar directamente)
     */
    @PostMapping
    public ResponseEntity<?> crearEmprendimiento(
            @RequestBody EmprendimientoRequestDTO request) {
        try {
            Integer id = emprendimientoService.estructuraEmprendimiento(request);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(Map.of(
                            "mensaje", "Emprendimiento creado exitosamente",
                            "id", id
                    ));
        } catch (Exception e) {
            log.error("Error al crear emprendimiento: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    /**
     * Obtener todos los emprendimientos públicos
     */
    @GetMapping
    public ResponseEntity<List<EmprendimientoResponseDTO>> obtenerEmprendimientos() {
        List<EmprendimientoResponseDTO> emprendimientos = emprendimientoService.obtenerEmprendimientos();
        return ResponseEntity.ok(emprendimientos);
    }

    /**
     * Obtener emprendimiento por ID (público - solo datos publicados)
     */
    @GetMapping("/{id}/publico")
    public ResponseEntity<?> obtenerEmprendimientoPublico(@PathVariable Integer id) {
        try {
            EmprendimientoResponseDTO emprendimiento = emprendimientoService
                    .obtenerEmprendimientoCompletoPorId(id);
            return ResponseEntity.ok(emprendimiento);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Emprendimiento no encontrado"));
        }
    }

    /**
     * Obtener mi emprendimiento (emprendedor - incluye datos pendientes si existen)
     */
    @GetMapping("/{id}/mi-emprendimiento")
    public ResponseEntity<?> obtenerMiEmprendimiento(
            @PathVariable Integer id,
            @AuthenticationPrincipal Usuarios usuario) {
        try {
            VistaEmprendedorDTO vista = emprendimientoService.obtenerVistaEmprendedor(id);
            return ResponseEntity.ok(vista);
        } catch (Exception e) {
            log.error("Error al obtener emprendimiento: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    /**
     * Actualizar emprendimiento (guarda cambios en borrador o crea solicitud)
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarEmprendimiento(
            @PathVariable Integer id,
            @RequestBody EmprendimientoRequestDTO request) {
        try {
            EmprendimientoResponseDTO actualizado = emprendimientoService
                    .actualizarEmprendimiento(id, request);
            return ResponseEntity.ok(Map.of(
                    "mensaje", "Emprendimiento actualizado exitosamente",
                    "emprendimiento", actualizado
            ));
        } catch (Exception e) {
            log.error("Error al actualizar emprendimiento: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    /**
     * Obtener emprendimientos por categoría
     */
    @GetMapping("/categoria/{categoriaId}")
    public ResponseEntity<?> obtenerPorCategoria(@PathVariable Integer categoriaId) {
        try {
            EmprendimientoPorCategoriaDTO resultado = emprendimientoService
                    .obtenerEmprendimientosPorCategoria(categoriaId);
            return ResponseEntity.ok(resultado);
        } catch (Exception e) {
            log.error("Error al obtener emprendimientos por categoría: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    /**
     * Enviar emprendimiento para aprobación
     */
    @PostMapping("/{id}/enviar-aprobacion")
    public ResponseEntity<?> enviarParaAprobacion(
            @PathVariable Integer id,
            @AuthenticationPrincipal Usuarios usuario) {
        try {
            SolicitudAprobacion solicitud = emprendimientoService.enviarParaAprobacion(id, usuario);
            return ResponseEntity.ok(Map.of(
                    "mensaje", "Emprendimiento enviado para aprobación",
                    "solicitudId", solicitud.getId(),
                    "estado", solicitud.getEstadoSolicitud()
            ));
        } catch (Exception e) {
            log.error("Error al enviar para aprobación: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    /**
     * Obtener todos los emprendimientos de un usuario autenticado
     */
    @GetMapping("/mis-emprendimientos")
    public ResponseEntity<?> obtenerEmprendimientosPorUsuario(@AuthenticationPrincipal Usuarios usuario) {
        try {
            List<EmprendimientoResponseDTO> emprendimientos = emprendimientoService.obtenerEmprendimientosPorUsuario(usuario);
            return ResponseEntity.ok(emprendimientos);
        } catch (Exception e) {
            log.error("Error al obtener emprendimientos del usuario: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", e.getMessage()));
        }
    }

}