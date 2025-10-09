package com.example.eureka.entrepreneurship.controller;

import com.example.eureka.entrepreneurship.dto.EventoRequestDTO;
import com.example.eureka.entrepreneurship.dto.EventoResponseDTO;
import com.example.eureka.entrepreneurship.service.EventosService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/eventos")
@RequiredArgsConstructor
public class EventosController {

    private final EventosService eventosService;

    @PostMapping("/crear")
    public ResponseEntity<EventoResponseDTO> crearEvento(
            @Valid @RequestBody EventoRequestDTO eventoRequest,
            @RequestParam Integer idUsuario) {
        EventoResponseDTO evento = eventosService.crearEvento(eventoRequest, idUsuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(evento);
    }

    @PutMapping("/editar/{idEvento}")
    public ResponseEntity<EventoResponseDTO> editarEvento(
            @PathVariable Integer idEvento,
            @Valid @RequestBody EventoRequestDTO eventoRequest,
            @RequestParam Integer idUsuario) {
        EventoResponseDTO evento = eventosService.editarEvento(idEvento, eventoRequest, idUsuario);
        return ResponseEntity.ok(evento);
    }

    @PatchMapping("/inactivar/{idEvento}")
    public ResponseEntity<String> inactivarEvento(
            @PathVariable Integer idEvento,
            @RequestParam Integer idUsuario) {
        eventosService.inactivarEvento(idEvento, idUsuario);
        return ResponseEntity.ok("Evento cancelado exitosamente");
    }

    @GetMapping("/listar")
    public ResponseEntity<List<EventoResponseDTO>> obtenerTodosLosEventos() {
        List<EventoResponseDTO> eventos = eventosService.obtenerTodosLosEventos();
        return ResponseEntity.ok(eventos);
    }

    @GetMapping("/emprendimiento/{idEmprendimiento}")
    public ResponseEntity<List<EventoResponseDTO>> obtenerEventosPorEmprendimiento(
            @PathVariable Integer idEmprendimiento) {
        List<EventoResponseDTO> eventos = eventosService.obtenerEventosPorEmprendimiento(idEmprendimiento);
        return ResponseEntity.ok(eventos);
    }

    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<EventoResponseDTO>> obtenerEventosPorUsuario(
            @PathVariable Integer idUsuario) {
        List<EventoResponseDTO> eventos = eventosService.obtenerEventosPorUsuario(idUsuario);
        return ResponseEntity.ok(eventos);
    }

    @GetMapping("/{idEvento}")
    public ResponseEntity<EventoResponseDTO> obtenerEventoPorId(@PathVariable Integer idEvento) {
        EventoResponseDTO evento = eventosService.obtenerEventoPorId(idEvento);
        return ResponseEntity.ok(evento);
    }
}
