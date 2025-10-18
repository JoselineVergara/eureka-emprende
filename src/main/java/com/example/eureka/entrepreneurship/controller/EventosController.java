package com.example.eureka.entrepreneurship.controller;

import com.example.eureka.entrepreneurship.dto.EventoRequestDTO;
import com.example.eureka.entrepreneurship.dto.EventoResponseDTO;
import com.example.eureka.entrepreneurship.service.IEventosService;
import com.example.eureka.enums.EstadoEvento;
import com.example.eureka.enums.TipoEvento;
import com.example.eureka.utilities.SecurityUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/v1/eventos")
@RequiredArgsConstructor
public class EventosController {

    private final IEventosService eventosServiceImpl;
    private final SecurityUtils securityUtils;

    @PostMapping("/crear")
    @PreAuthorize("hasRole('ADMINISTRADOR') or hasRole('EMPRENDEDOR')")
    public ResponseEntity<EventoResponseDTO> crearEvento(
            @Valid @RequestBody EventoRequestDTO eventoRequest,
            @RequestParam Integer idEmprendimiento) {
        Integer idUsuario = securityUtils.getIdUsuarioAutenticado();
        EventoResponseDTO evento = eventosServiceImpl.crearEvento(eventoRequest, idEmprendimiento);
        return ResponseEntity.status(HttpStatus.CREATED).body(evento);
    }

    @PutMapping("/editar/{idEvento}/{idEmprendimiento}")
    @PreAuthorize("hasRole('ADMINISTRADOR') or hasRole('EMPRENDEDOR')")
    public ResponseEntity<EventoResponseDTO> editarEvento(
            @PathVariable Integer idEvento,
            @PathVariable Integer idEmprendimiento,
            @Valid @RequestBody EventoRequestDTO eventoRequest) {
        Integer idUsuario = securityUtils.getIdUsuarioAutenticado();
        EventoResponseDTO evento = eventosServiceImpl.editarEvento(idEvento, eventoRequest, idEmprendimiento);
        return ResponseEntity.ok(evento);
    }

    @PutMapping("/cancelar/{idEvento}")
    @PreAuthorize("hasRole('ADMINISTRADOR') or hasRole('EMPRENDEDOR')")
    public ResponseEntity<String> inactivarEvento(
            @PathVariable Integer idEvento) {
        Integer idUsuario = securityUtils.getIdUsuarioAutenticado();
        eventosServiceImpl.inactivarEvento(idEvento);
        return ResponseEntity.ok("Evento cancelado exitosamente");
    }

    @PutMapping("/activar/{idEvento}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<String> activarEvento(
            @PathVariable Integer idEvento) {
        eventosServiceImpl.activarEvento(idEvento);
        return ResponseEntity.ok("Evento activado exitosamente");
    }

    @PutMapping("/inactivar/{idEvento}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<String> desactivarEvento(
            @PathVariable Integer idEvento) {
        eventosServiceImpl.desactivarEvento(idEvento);
        return ResponseEntity.ok("Evento desactivado exitosamente");
    }

    @GetMapping("/emprendimiento/{idEmprendimiento}")
    public ResponseEntity<List<EventoResponseDTO>> obtenerEventosPorEmprendimiento(
            @PathVariable Integer idEmprendimiento) {
        List<EventoResponseDTO> eventos = eventosServiceImpl.obtenerEventosPorEmprendimiento(idEmprendimiento);
        return ResponseEntity.ok(eventos);
    }

    /**
     * Obtiene eventos del usuario autenticado con filtros opcionales
     *
     * @param tipoEvento (Opcional) Tipo de evento: presencial o virtual
     * @param idEmprendimiento (Opcional) ID del emprendimiento para filtrar
     * @return Lista de eventos del usuario autenticado filtrados según los criterios
     */
    @GetMapping("/usuario")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<EventoResponseDTO>> obtenerEventosPorUsuario(
            @RequestParam(required = false) TipoEvento tipoEvento,
            @RequestParam(required = false) Integer idEmprendimiento) {
        Integer idUsuario = securityUtils.getIdUsuarioAutenticado();
        List<EventoResponseDTO> eventos = eventosServiceImpl.obtenerEventosPorUsuario(idUsuario, tipoEvento, idEmprendimiento);
        return ResponseEntity.ok(eventos);
    }

    @GetMapping("/{idEvento}")
    public ResponseEntity<EventoResponseDTO> obtenerEventoPorId(@PathVariable Integer idEvento) {
        EventoResponseDTO evento = eventosServiceImpl.obtenerEventoPorId(idEvento);
        return ResponseEntity.ok(evento);
    }

    /**
     * Endpoint para filtrar eventos con múltiples criterios (uso general/admin)
     *
     * @param estado (Opcional) Estado del evento: programado, en_curso, terminado, cancelado
     * @param tipoEvento (Opcional) Tipo de evento: presencial o virtual
     * @param idEmprendimiento (Opcional) ID del emprendimiento
     * @param fechaInicio (Opcional) Fecha de inicio del rango - requerida junto con fechaFin
     * @param fechaFin (Opcional) Fecha de fin del rango - requerida junto con fechaInicio
     * @return Lista de eventos filtrados según los criterios especificados
     */
    @GetMapping("/filtrar")
    public ResponseEntity<List<EventoResponseDTO>> obtenerEventosFiltrados(
            @RequestParam(required = false) EstadoEvento estado,
            @RequestParam(required = false) TipoEvento tipoEvento,
            @RequestParam(required = false) Integer idEmprendimiento,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaInicio,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaFin) {

        List<EventoResponseDTO> eventos = eventosServiceImpl.obtenerEventosFiltrados(
                estado, tipoEvento, idEmprendimiento, fechaInicio, fechaFin);
        return ResponseEntity.ok(eventos);
    }
}