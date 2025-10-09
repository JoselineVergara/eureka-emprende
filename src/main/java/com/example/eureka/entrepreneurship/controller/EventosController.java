package com.example.eureka.entrepreneurship.controller;

import com.example.eureka.entrepreneurship.dto.EventoRequestDTO;
import com.example.eureka.entrepreneurship.dto.EventoResponseDTO;
import com.example.eureka.entrepreneurship.service.IEventosService;
import com.example.eureka.entrepreneurship.service.impl.EventosServiceImpl;
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

    private final IEventosService eventosServiceImpl;

    @PostMapping("/crear")
    public ResponseEntity<EventoResponseDTO> crearEvento(
            @Valid @RequestBody EventoRequestDTO eventoRequest,
            @RequestParam Integer idUsuario) {
        EventoResponseDTO evento = eventosServiceImpl.crearEvento(eventoRequest, idUsuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(evento);
    }

    @PutMapping("/editar/{idEvento}")
    public ResponseEntity<EventoResponseDTO> editarEvento(
            @PathVariable Integer idEvento,
            @Valid @RequestBody EventoRequestDTO eventoRequest,
            @RequestParam Integer idUsuario) {
        EventoResponseDTO evento = eventosServiceImpl.editarEvento(idEvento, eventoRequest, idUsuario);
        return ResponseEntity.ok(evento);
    }

    @PatchMapping("/inactivar/{idEvento}")
    public ResponseEntity<String> inactivarEvento(
            @PathVariable Integer idEvento,
            @RequestParam Integer idUsuario) {
        eventosServiceImpl.inactivarEvento(idEvento, idUsuario);
        return ResponseEntity.ok("Evento cancelado exitosamente");
    }

    @GetMapping("/listar")
    public ResponseEntity<List<EventoResponseDTO>> obtenerTodosLosEventos() {
        List<EventoResponseDTO> eventos = eventosServiceImpl.obtenerTodosLosEventos();
        return ResponseEntity.ok(eventos);
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


}
