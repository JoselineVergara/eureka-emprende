package com.example.eureka.entrepreneurship.controller;

import com.example.eureka.entrepreneurship.dto.EventoRequestDTO;
import com.example.eureka.entrepreneurship.dto.EventoResponseDTO;
import com.example.eureka.entrepreneurship.service.IEventosService;
import com.example.eureka.enums.EstadoEvento;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/v1/eventos")
@RequiredArgsConstructor
public class EventosController {

    private final IEventosService eventosServiceImpl;

    @PostMapping("/crear")
    public ResponseEntity<EventoResponseDTO> crearEvento(
            @Valid @RequestBody EventoRequestDTO eventoRequest,
            @RequestParam Integer idEmprendimiento) {
        EventoResponseDTO evento = eventosServiceImpl.crearEvento(eventoRequest, idEmprendimiento);
        return ResponseEntity.status(HttpStatus.CREATED).body(evento);
    }

    @PutMapping("/editar/{idEvento}/{idEmprendimiento}")
    public ResponseEntity<EventoResponseDTO> editarEvento(
            @PathVariable Integer idEvento,
            @PathVariable Integer idEmprendimiento,
            @Valid @RequestBody EventoRequestDTO eventoRequest) {
        EventoResponseDTO evento = eventosServiceImpl.editarEvento(idEvento, eventoRequest, idEmprendimiento);
        return ResponseEntity.ok(evento);
    }

    @PutMapping("/inactivar/{idEvento}")
    public ResponseEntity<String> inactivarEvento(
            @PathVariable Integer idEvento) {
        eventosServiceImpl.inactivarEvento(idEvento);
        return ResponseEntity.ok("Evento cancelado exitosamente");
    }

    @GetMapping("/emprendimiento/{idEmprendimiento}")
    public ResponseEntity<List<EventoResponseDTO>> obtenerEventosPorEmprendimiento(
            @PathVariable Integer idEmprendimiento) {
        List<EventoResponseDTO> eventos = eventosServiceImpl.obtenerEventosPorEmprendimiento(idEmprendimiento);
        return ResponseEntity.ok(eventos);
    }

    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<EventoResponseDTO>> obtenerEventosPorUsuario(
            @PathVariable Integer idUsuario) {
        List<EventoResponseDTO> eventos = eventosServiceImpl.obtenerEventosPorUsuario(idUsuario);
        return ResponseEntity.ok(eventos);
    }

    @GetMapping("/{idEvento}")
    public ResponseEntity<EventoResponseDTO> obtenerEventoPorId(@PathVariable Integer idEvento) {
        EventoResponseDTO evento = eventosServiceImpl.obtenerEventoPorId(idEvento);
        return ResponseEntity.ok(evento);
    }

    @GetMapping("/filtrar")
    public ResponseEntity<List<EventoResponseDTO>> obtenerEventosFiltrados(
            @RequestParam(required = false) EstadoEvento estado,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaInicio,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaFin) {
        List<EventoResponseDTO> eventos = eventosServiceImpl.obtenerEventosFiltrados(
                estado, fechaInicio, fechaFin);
        return ResponseEntity.ok(eventos);
    }

}
