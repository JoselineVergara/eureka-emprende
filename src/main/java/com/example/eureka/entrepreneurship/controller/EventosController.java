package com.example.eureka.entrepreneurship.controller;

import com.example.eureka.entrepreneurship.dto.EventoRequestDTO;
import com.example.eureka.entrepreneurship.dto.EventoResponseDTO;
import com.example.eureka.entrepreneurship.dto.EventoAdminDTO;
import com.example.eureka.entrepreneurship.dto.EventoEmprendedorDTO;
import com.example.eureka.entrepreneurship.dto.EventoPublicoDTO;
import com.example.eureka.entrepreneurship.service.IEventosService;
import com.example.eureka.enums.EstadoEvento;
import com.example.eureka.enums.TipoEvento;
import com.example.eureka.entrepreneurship.dto.PageResponseDTO;
import com.example.eureka.utilities.SecurityUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/v1/eventos")
@RequiredArgsConstructor
public class EventosController {

    private final IEventosService eventosService;
    private final SecurityUtils securityUtils;

    // ========== ENDPOINTS PÚBLICOS ==========

    @GetMapping("/publico")
    public ResponseEntity<PageResponseDTO<EventoPublicoDTO>> obtenerEventosPublicos(
            @RequestParam(required = false) String titulo,
            @RequestParam(required = false) Integer mes,
            @RequestParam(required = false) TipoEvento tipoEvento,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        PageResponseDTO<EventoPublicoDTO> eventos = eventosService.obtenerEventosPublicos(
                titulo, mes, tipoEvento, pageable);
        return ResponseEntity.ok(eventos);
    }

    @GetMapping("/publico/{idEvento}")
    public ResponseEntity<EventoResponseDTO> obtenerEventoPublicoPorId(
            @PathVariable Integer idEvento) {
        EventoResponseDTO evento = eventosService.obtenerEventoPublicoPorId(idEvento);
        return ResponseEntity.ok(evento);
    }

    // ========== ENDPOINTS EMPRENDEDOR ==========

    @GetMapping("/emprendedor")
    @PreAuthorize("hasRole('EMPRENDEDOR')")
    public ResponseEntity<PageResponseDTO<EventoEmprendedorDTO>> obtenerEventosEmprendedor(
            @RequestParam(required = false) String titulo,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaInicio,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaFin,
            @RequestParam(required = false) EstadoEvento estado,
            @RequestParam(required = false) TipoEvento tipoEvento,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Integer idUsuario = securityUtils.getIdUsuarioAutenticado();
        Pageable pageable = PageRequest.of(page, size);
        PageResponseDTO<EventoEmprendedorDTO> eventos = eventosService.obtenerEventosEmprendedor(
                idUsuario, titulo, fechaInicio, fechaFin, estado, tipoEvento, pageable);
        return ResponseEntity.ok(eventos);
    }

    @PostMapping("/emprendedor/crear")
    @PreAuthorize("hasRole('EMPRENDEDOR')")
    public ResponseEntity<EventoResponseDTO> crearEvento(
            @Valid @RequestBody EventoRequestDTO request,
            @RequestParam Integer idEmprendimiento) {
        Integer idUsuario = securityUtils.getIdUsuarioAutenticado();
        EventoResponseDTO evento = eventosService.crearEvento(request, idEmprendimiento, idUsuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(evento);
    }

    @PutMapping("/emprendedor/{idEvento}")
    @PreAuthorize("hasRole('EMPRENDEDOR')")
    public ResponseEntity<EventoResponseDTO> editarEvento(
            @PathVariable Integer idEvento,
            @RequestParam Integer idEmprendimiento,
            @Valid @RequestBody EventoRequestDTO request) {
        Integer idUsuario = securityUtils.getIdUsuarioAutenticado();
        EventoResponseDTO evento = eventosService.editarEvento(idEvento, request, idEmprendimiento, idUsuario);
        return ResponseEntity.ok(evento);
    }

    @PutMapping("/emprendedor/{idEvento}/cancelar")
    @PreAuthorize("hasRole('EMPRENDEDOR')")
    public ResponseEntity<String> cancelarEvento(@PathVariable Integer idEvento) {
        Integer idUsuario = securityUtils.getIdUsuarioAutenticado();
        eventosService.cancelarEvento(idEvento, idUsuario);
        return ResponseEntity.ok("Evento cancelado exitosamente");
    }

    // ========== ENDPOINTS ADMIN ==========

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<PageResponseDTO<EventoAdminDTO>> obtenerEventosAdmin(
            @RequestParam(required = false) String titulo,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaInicio,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaFin,
            @RequestParam(required = false) EstadoEvento estado,
            @RequestParam(required = false) TipoEvento tipoEvento,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        PageResponseDTO<EventoAdminDTO> eventos = eventosService.obtenerEventosAdmin(
                titulo, fechaInicio, fechaFin, estado, tipoEvento, pageable);
        return ResponseEntity.ok(eventos);
    }

    @GetMapping("/admin/{idEvento}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<EventoResponseDTO> obtenerEventoPorId(
            @PathVariable Integer idEvento) {
        EventoResponseDTO evento = eventosService.obtenerEventoPorId(idEvento);
        return ResponseEntity.ok(evento);
    }

    @PutMapping("/admin/{idEvento}/activar")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<String> activarEvento(@PathVariable Integer idEvento) {
        eventosService.activarEvento(idEvento);
        return ResponseEntity.ok("Evento activado exitosamente");
    }

    @PutMapping("/admin/{idEvento}/desactivar")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<String> desactivarEvento(@PathVariable Integer idEvento) {
        eventosService.desactivarEvento(idEvento);
        return ResponseEntity.ok("Evento desactivado exitosamente");
    }
}