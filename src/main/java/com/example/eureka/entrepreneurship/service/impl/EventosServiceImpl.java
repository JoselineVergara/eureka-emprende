package com.example.eureka.entrepreneurship.service.impl;

import com.example.eureka.config.BusinessException;
import com.example.eureka.entrepreneurship.dto.EventoRequestDTO;
import com.example.eureka.entrepreneurship.dto.EventoResponseDTO;
import com.example.eureka.entrepreneurship.repository.IEmprendimientosRepository;
import com.example.eureka.entrepreneurship.service.IEventosService;
import com.example.eureka.enums.EstadoEmprendimiento;
import com.example.eureka.enums.EstadoEvento;
import com.example.eureka.general.repository.IMultimediaRepository;
import com.example.eureka.model.Emprendimientos;
import com.example.eureka.model.Eventos;
import com.example.eureka.model.Multimedia;
import com.example.eureka.entrepreneurship.repository.IEventosRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventosServiceImpl implements IEventosService {

    private final IEventosRepository IEventosRepository;
    private final IEmprendimientosRepository emprendimientosRepository;
    private final IMultimediaRepository multimediaRepository;

    @Transactional
    public EventoResponseDTO crearEvento(EventoRequestDTO request, Integer idEmprendimiento) {
        // Validar que el emprendimiento existe y está aprobado
        Emprendimientos emprendimiento = validarEmprendimientoAprobado(idEmprendimiento);

        // Validar que la multimedia existe
        Multimedia multimedia = multimediaRepository.findById(request.getIdMultimedia())
                .orElseThrow(() -> new BusinessException("Multimedia no encontrada"));

        // Validar fecha del evento (debe ser futura)
        if (request.getFechaEvento().isBefore(LocalDateTime.now())) {
            throw new BusinessException("La fecha del evento debe ser futura");
        }

        // Crear el evento
        Eventos evento = new Eventos();
        evento.setTitulo(request.getTitulo());
        evento.setDescripcion(request.getDescripcion());
        evento.setFechaEvento(request.getFechaEvento());
        evento.setLugar(request.getLugar());
        evento.setTipoEvento(request.getTipoEvento());
        evento.setLinkInscripcion(request.getLinkInscripcion());
        evento.setDireccion(request.getDireccion());
        evento.setEstadoEvento(EstadoEvento.programado);
        evento.setFechaCreacion(LocalDateTime.now());
        evento.setEmprendimiento(emprendimiento);
        evento.setMultimedia(multimedia);

        Eventos eventoGuardado = IEventosRepository.save(evento);

        return convertirADTO(eventoGuardado);
    }

    @Transactional
    public EventoResponseDTO editarEvento(Integer idEvento, EventoRequestDTO request, Integer idEmprendimiento) {
        Eventos evento = IEventosRepository.findById(idEvento)
                .orElseThrow(() -> new BusinessException("Evento no encontrado"));

        // Validar que el evento pertenece al emprendimiento indicado
        if (!evento.getEmprendimiento().getId().equals(idEmprendimiento)) {
            throw new BusinessException("El evento no pertenece a este emprendimiento");
        }

        validarEmprendimientoAprobado(idEmprendimiento);

        if (evento.getEstadoEvento() == EstadoEvento.cancelado ||
                evento.getEstadoEvento() == EstadoEvento.terminado) {
            throw new BusinessException("No se puede editar un evento cancelado o terminado");
        }

        if (request.getFechaEvento().isBefore(LocalDateTime.now())) {
            throw new BusinessException("La fecha del evento debe ser futura");
        }

        evento.setTitulo(request.getTitulo());
        evento.setDescripcion(request.getDescripcion());
        evento.setFechaEvento(request.getFechaEvento());
        evento.setLugar(request.getLugar());
        evento.setTipoEvento(request.getTipoEvento());
        evento.setLinkInscripcion(request.getLinkInscripcion());
        evento.setDireccion(request.getDireccion());
        evento.setFechaModificacion(LocalDateTime.now());

        Eventos eventoActualizado = IEventosRepository.save(evento);

        return convertirADTO(eventoActualizado);
    }

    @Transactional
    public void inactivarEvento(Integer idEvento) {
        Eventos evento = IEventosRepository.findById(idEvento)
                .orElseThrow(() -> new BusinessException("Evento no encontrado"));

        validarEmprendimientoAprobado(evento.getEmprendimiento().getId());

        if (evento.getEstadoEvento() == EstadoEvento.cancelado) {
            throw new BusinessException("El evento ya está cancelado");
        }
        if (evento.getEstadoEvento() == EstadoEvento.terminado) {
            throw new BusinessException("No se puede cancelar un evento terminado");
        }

        evento.setEstadoEvento(EstadoEvento.cancelado);
        evento.setFechaModificacion(LocalDateTime.now());

        IEventosRepository.save(evento);
    }
    @Transactional(readOnly = true)
    public List<EventoResponseDTO> obtenerEventosPorEmprendimiento(Integer idEmprendimiento) {
        List<Eventos> eventos = IEventosRepository.findByEmprendimientoId(idEmprendimiento);
        return eventos.stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<EventoResponseDTO> obtenerEventosPorUsuario(Integer idUsuario) {
        List<Eventos> eventos = IEventosRepository.findByEmprendimiento_Usuarios_Id(idUsuario);
        return eventos.stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public EventoResponseDTO obtenerEventoPorId(Integer idEvento) {
        Eventos evento = IEventosRepository.findById(idEvento)
                .orElseThrow(() -> new BusinessException("Evento no encontrado"));
        return convertirADTO(evento);
    }

    // ===== MÉTODO UNIFICADO CON FILTROS OPCIONALES =====
    @Transactional(readOnly = true)
    public List<EventoResponseDTO> obtenerEventosFiltrados(
            EstadoEvento estadoEvento,
            LocalDateTime fechaInicio,
            LocalDateTime fechaFin) {

        List<Eventos> eventos;

        // Validar fechas si ambas están presentes
        if (fechaInicio != null && fechaFin != null && fechaInicio.isAfter(fechaFin)) {
            throw new BusinessException("La fecha de inicio debe ser anterior a la fecha de fin");
        }

        // Caso 1: Solo estado
        if (estadoEvento != null && fechaInicio == null && fechaFin == null) {
            eventos = IEventosRepository.findByEstadoEvento(estadoEvento);
        }
        // Caso 2: Solo rango de fechas (ambas deben estar presentes)
        else if (estadoEvento == null && fechaInicio != null && fechaFin != null) {
            eventos = IEventosRepository.findByFechaEventoBetween(fechaInicio, fechaFin);
        }
        // Caso 3: Estado y rango de fechas
        else if (estadoEvento != null && fechaInicio != null && fechaFin != null) {
            eventos = IEventosRepository.findByEstadoEventoAndFechaEventoBetween(
                    estadoEvento, fechaInicio, fechaFin);
        }
        // Caso 4: Sin filtros (traer todos)
        else if (estadoEvento == null && fechaInicio == null && fechaFin == null) {
            eventos = IEventosRepository.findAll();
        }
        // Caso 5: Solo una fecha (no válido)
        else {
            throw new BusinessException(
                    "Para filtrar por fecha, debe proporcionar tanto fechaInicio como fechaFin");
        }

        return eventos.stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    private Emprendimientos validarEmprendimientoAprobado(Integer idEmprendimiento) {
        Emprendimientos emprendimiento = emprendimientosRepository.findById(idEmprendimiento)
                .orElseThrow(() -> new BusinessException("Emprendimiento no encontrado"));

        // Validar que el emprendimiento está aprobado
        if (!emprendimiento.getEstadoEmprendimiento().equalsIgnoreCase("APROBADO")) {
            throw new BusinessException("El emprendimiento debe estar aprobado para crear eventos");
        }

        return emprendimiento;
    }

    private EventoResponseDTO convertirADTO(Eventos evento) {
        return EventoResponseDTO.builder()
                .idEvento(evento.getIdEvento())
                .titulo(evento.getTitulo())
                .descripcion(evento.getDescripcion())
                .fechaEvento(evento.getFechaEvento())
                .lugar(evento.getLugar())
                .tipoEvento(evento.getTipoEvento())
                .linkInscripcion(evento.getLinkInscripcion())
                .direccion(evento.getDireccion())
                .estadoEvento(evento.getEstadoEvento())
                .fechaCreacion(evento.getFechaCreacion())
                .fechaModificacion(evento.getFechaModificacion())
                .idEmprendimiento(evento.getEmprendimiento().getId())
                .nombreEmprendimiento(evento.getEmprendimiento().getNombreComercial())
                .idMultimedia(evento.getMultimedia().getId())
                // .urlMultimedia(evento.getMultimedia().getUrlArchivo())
                .build();
    }
}