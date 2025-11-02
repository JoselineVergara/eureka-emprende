package com.example.eureka.entrepreneurship.service.impl;

import com.example.eureka.exception.BusinessException;
import com.example.eureka.infrastructure.storage.FileStorageService;
import com.example.eureka.entrepreneurship.dto.EventoRequestDTO;
import com.example.eureka.entrepreneurship.dto.EventoResponseDTO;
import com.example.eureka.entrepreneurship.dto.EventoAdminDTO;
import com.example.eureka.entrepreneurship.dto.EventoEmprendedorDTO;
import com.example.eureka.entrepreneurship.dto.EventoPublicoDTO;
import com.example.eureka.entrepreneurship.repository.IEmprendimientosRepository;
import com.example.eureka.entrepreneurship.repository.IEventosRepository;
import com.example.eureka.entrepreneurship.service.IEventosService;
import com.example.eureka.entrepreneurship.specification.EventoSpecification;
import com.example.eureka.domain.enums.EstadoEvento;
import com.example.eureka.domain.enums.TipoEvento;
import com.example.eureka.general.repository.IMultimediaRepository;
import com.example.eureka.domain.model.Emprendimientos;
import com.example.eureka.domain.model.Eventos;
import com.example.eureka.domain.model.Multimedia;
import com.example.eureka.entrepreneurship.dto.PageResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class EventosServiceImpl implements IEventosService {

    private final IEventosRepository eventosRepository;
    private final IEmprendimientosRepository emprendimientosRepository;
    private final IMultimediaRepository multimediaRepository;
    private final FileStorageService fileStorageService;

    @Transactional
    @Override
    public EventoResponseDTO crearEvento(EventoRequestDTO dto, Integer idEmprendimiento, Integer idUsuario) {
        if (dto == null) {
            throw new BusinessException("El DTO no puede ser nulo");
        }

        Emprendimientos emprendimiento = validarEmprendimientoAprobado(idEmprendimiento);
        validarPropietarioEmprendimiento(emprendimiento, idUsuario);

        // Subir imagen si fue enviada
        Multimedia multimedia = null;
        if (dto.getImagen() != null && !dto.getImagen().isEmpty()) {
            try {
                String urlArchivo = fileStorageService.uploadFile(dto.getImagen());
                multimedia = new Multimedia();
                multimedia.setUrlArchivo(urlArchivo);
                multimedia.setNombreActivo(dto.getImagen().getOriginalFilename());
                multimedia.setDescripcion("Imagen de evento");
                multimedia = multimediaRepository.save(multimedia);
            } catch (IOException e) {
                throw new BusinessException("Error al subir el archivo: " + e.getMessage());
            }
        } else {
            throw new BusinessException("La imagen es obligatoria para el evento");
        }

        if (dto.getFechaEvento().isBefore(LocalDateTime.now())) {
            throw new BusinessException("La fecha del evento debe ser futura");
        }

        Eventos evento = new Eventos();
        evento.setTitulo(dto.getTitulo());
        evento.setDescripcion(dto.getDescripcion());
        evento.setFechaEvento(dto.getFechaEvento());
        evento.setLugar(dto.getLugar());
        evento.setTipoEvento(dto.getTipoEvento());
        evento.setLinkInscripcion(dto.getLinkInscripcion());
        evento.setDireccion(dto.getDireccion());
        evento.setEstadoEvento(EstadoEvento.programado);
        evento.setFechaCreacion(LocalDateTime.now());
        evento.setActivo(true);
        evento.setEmprendimiento(emprendimiento);
        evento.setMultimedia(multimedia);

        Eventos eventoGuardado = eventosRepository.save(evento);
        return convertirAResponseDTO(eventoGuardado);
    }


    @Transactional
    @Override
    public EventoResponseDTO editarEvento(Integer idEvento, EventoRequestDTO dto, Integer idEmprendimiento, Integer idUsuario) {
        Eventos evento = eventosRepository.findById(idEvento)
                .orElseThrow(() -> new BusinessException("Evento no encontrado"));

        if (!evento.getEmprendimiento().getId().equals(idEmprendimiento)) {
            throw new BusinessException("El evento no pertenece a este emprendimiento");
        }

        validarEmprendimientoAprobado(idEmprendimiento);
        validarPropietarioEmprendimiento(evento.getEmprendimiento(), idUsuario);

        if (evento.getEstadoEvento() == EstadoEvento.cancelado ||
                evento.getEstadoEvento() == EstadoEvento.terminado) {
            throw new BusinessException("No se puede editar un evento cancelado o terminado");
        }

        if (dto.getFechaEvento().isBefore(LocalDateTime.now())) {
            throw new BusinessException("La fecha del evento debe ser futura");
        }

        // ACTUALIZAR IMAGEN SI VIENE EN DTO
        if (dto.getImagen() != null && !dto.getImagen().isEmpty()) {
            try {
                // Eliminar la imagen vieja si existe
                if (evento.getMultimedia() != null) {
                    String oldUrl = evento.getMultimedia().getUrlArchivo();
                    String oldFileName = extractFileNameFromUrl(oldUrl);
                    if (oldFileName != null) {
                        fileStorageService.deleteFile(oldFileName);
                    }
                    multimediaRepository.delete(evento.getMultimedia());
                }
                // Subir nueva imagen
                String urlArchivo = fileStorageService.uploadFile(dto.getImagen());
                Multimedia multimedia = new Multimedia();
                multimedia.setUrlArchivo(urlArchivo);
                multimedia.setNombreActivo(dto.getImagen().getOriginalFilename());
                multimedia.setDescripcion("Imagen de evento");
                multimedia = multimediaRepository.save(multimedia);

                evento.setMultimedia(multimedia);
            } catch (IOException e) {
                throw new BusinessException("Error al actualizar la imagen: " + e.getMessage());
            }
        }

        evento.setTitulo(dto.getTitulo());
        evento.setDescripcion(dto.getDescripcion());
        evento.setFechaEvento(dto.getFechaEvento());
        evento.setLugar(dto.getLugar());
        evento.setTipoEvento(dto.getTipoEvento());
        evento.setLinkInscripcion(dto.getLinkInscripcion());
        evento.setDireccion(dto.getDireccion());
        evento.setFechaModificacion(LocalDateTime.now());

        Eventos eventoActualizado = eventosRepository.save(evento);
        return convertirAResponseDTO(eventoActualizado);
    }

    @Transactional
    @Override
    public void cancelarEvento(Integer idEvento, Integer idUsuario) {
        Eventos evento = eventosRepository.findById(idEvento)
                .orElseThrow(() -> new BusinessException("Evento no encontrado"));

        validarPropietarioEmprendimiento(evento.getEmprendimiento(), idUsuario);

        if (evento.getEstadoEvento() == EstadoEvento.cancelado) {
            throw new BusinessException("El evento ya está cancelado");
        }
        if (evento.getEstadoEvento() == EstadoEvento.terminado) {
            throw new BusinessException("No se puede cancelar un evento terminado");
        }

        evento.setEstadoEvento(EstadoEvento.cancelado);
        evento.setFechaModificacion(LocalDateTime.now());
        eventosRepository.save(evento);
    }

    @Transactional
    @Override
    public void activarEvento(Integer idEvento) {
        Eventos evento = eventosRepository.findById(idEvento)
                .orElseThrow(() -> new BusinessException("Evento no encontrado"));

        if (evento.isActivo()) {
            throw new BusinessException("El evento ya está activado");
        }

        evento.setActivo(true);
        evento.setFechaModificacion(LocalDateTime.now());
        eventosRepository.save(evento);
    }

    @Transactional
    @Override
    public void desactivarEvento(Integer idEvento) {
        Eventos evento = eventosRepository.findById(idEvento)
                .orElseThrow(() -> new BusinessException("Evento no encontrado"));

        if (!evento.isActivo()) {
            throw new BusinessException("El evento ya está desactivado");
        }

        evento.setActivo(false);
        evento.setFechaModificacion(LocalDateTime.now());
        eventosRepository.save(evento);
    }

    @Transactional(readOnly = true)
    @Override
    public PageResponseDTO<EventoPublicoDTO> obtenerEventosPublicos(
            String titulo,
            Integer mes,
            TipoEvento tipoEvento,
            Pageable pageable) {

        if (mes == null) {
            throw new BusinessException("El parámetro 'mes' es obligatorio para esta consulta.");
        }

        Specification<Eventos> spec = EventoSpecification.conFiltros(
                titulo,
                null,
                null,
                EstadoEvento.programado, // Solo programados
                tipoEvento,
                mes
        );

        Page<Eventos> eventos = eventosRepository.findAll(spec, pageable);
        Page<EventoPublicoDTO> eventosDTO = eventos.map(this::convertirAPublicoDTO);
        return PageResponseDTO.fromPage(eventosDTO);
    }

    @Transactional(readOnly = true)
    @Override
    public EventoResponseDTO obtenerEventoPublicoPorId(Integer idEvento) {
        Eventos evento = eventosRepository.findById(idEvento)
                .orElseThrow(() -> new BusinessException("Evento no encontrado"));

        if (evento.getEstadoEvento() != EstadoEvento.programado) {
            throw new BusinessException("Evento no disponible");
        }

        return convertirAResponseDTO(evento);
    }

    @Transactional(readOnly = true)
    @Override
    public PageResponseDTO<EventoEmprendedorDTO> obtenerEventosEmprendedor(
            Integer idUsuario,
            String titulo,
            LocalDateTime fechaInicio,
            LocalDateTime fechaFin,
            EstadoEvento estado,
            TipoEvento tipoEvento,
            Pageable pageable) {

        Specification<Eventos> spec = EventoSpecification.porUsuarioEmprendedor(idUsuario)
                .and(EventoSpecification.conFiltros(titulo, fechaInicio, fechaFin, estado, tipoEvento, null));

        Page<Eventos> eventos = eventosRepository.findAll(spec, pageable);
        Page<EventoEmprendedorDTO> eventosDTO = eventos.map(this::convertirAEmprendedorDTO);
        return PageResponseDTO.fromPage(eventosDTO);
    }

    @Transactional(readOnly = true)
    @Override
    public PageResponseDTO<EventoAdminDTO> obtenerEventosAdmin(
            String titulo,
            LocalDateTime fechaInicio,
            LocalDateTime fechaFin,
            EstadoEvento estado,
            TipoEvento tipoEvento,
            Pageable pageable) {

        Specification<Eventos> spec = EventoSpecification.conFiltros(
                titulo, fechaInicio, fechaFin, estado, tipoEvento, null);

        Page<Eventos> eventos = eventosRepository.findAll(spec, pageable);
        Page<EventoAdminDTO> eventosDTO = eventos.map(this::convertirAAdminDTO);
        return PageResponseDTO.fromPage(eventosDTO);
    }

    @Transactional(readOnly = true)
    @Override
    public EventoResponseDTO obtenerEventoPorId(Integer idEvento) {
        Eventos evento = eventosRepository.findById(idEvento)
                .orElseThrow(() -> new BusinessException("Evento no encontrado"));
        return convertirAResponseDTO(evento);
    }

    // Métodos auxiliares

    private Emprendimientos validarEmprendimientoAprobado(Integer idEmprendimiento) {
        Emprendimientos emprendimiento = emprendimientosRepository.findById(idEmprendimiento)
                .orElseThrow(() -> new BusinessException("Emprendimiento no encontrado"));

        if (!emprendimiento.getEstadoEmprendimiento().equalsIgnoreCase("APROBADO")) {
            throw new BusinessException("El emprendimiento debe estar aprobado para crear eventos");
        }

        return emprendimiento;
    }

    private String extractFileNameFromUrl(String url) {
        if (url == null || url.isEmpty()) {
            return null;
        }

        try {
            // Obtener la última parte de la URL después del último '/'
            int lastSlashIndex = url.lastIndexOf('/');
            if (lastSlashIndex != -1 && lastSlashIndex < url.length() - 1) {
                String fileName = url.substring(lastSlashIndex + 1);
                // Remover parámetros query si existen (por si hay URLs prefirmadas)
                int queryIndex = fileName.indexOf('?');
                if (queryIndex != -1) {
                    fileName = fileName.substring(0, queryIndex);
                }
                return fileName;
            }
        } catch (Exception e) {
            System.err.println("Error al extraer nombre de archivo de URL: " + e.getMessage());
        }

        return null;
    }


    private void validarPropietarioEmprendimiento(Emprendimientos emprendimiento, Integer idUsuario) {
        if (!emprendimiento.getUsuarios().getId().equals(idUsuario)) {
            throw new BusinessException("No tienes permisos para esta acción");
        }
    }

    private EventoPublicoDTO convertirAPublicoDTO(Eventos evento) {
        return EventoPublicoDTO.builder()
                .idEvento(evento.getIdEvento())
                .titulo(evento.getTitulo())
                .descripcion(evento.getDescripcion())
                .fechaEvento(evento.getFechaEvento())
                .tipoEvento(evento.getTipoEvento())
                .idMultimedia(evento.getMultimedia().getId())
                .urlMultimedia(evento.getMultimedia().getUrlArchivo())
                .build();
    }

    private EventoEmprendedorDTO convertirAEmprendedorDTO(Eventos evento) {
        return EventoEmprendedorDTO.builder()
                .idEvento(evento.getIdEvento())
                .titulo(evento.getTitulo())
                .descripcion(evento.getDescripcion())
                .fechaEvento(evento.getFechaEvento())
                .lugar(evento.getLugar())
                .tipoEvento(evento.getTipoEvento())
                .estadoEvento(evento.getEstadoEvento())
                .linkInscripcion(evento.getLinkInscripcion())
                .direccion(evento.getDireccion())
                .idMultimedia(evento.getMultimedia().getId())
                .build();
    }

    private EventoAdminDTO convertirAAdminDTO(Eventos evento) {
        return EventoAdminDTO.builder()
                .idEvento(evento.getIdEvento())
                .titulo(evento.getTitulo())
                .idEmprendimiento(evento.getEmprendimiento().getId())
                .nombreEmprendimiento(evento.getEmprendimiento().getNombreComercial())
                .fechaEvento(evento.getFechaEvento())
                .fechaCreacion(evento.getFechaCreacion())
                .estadoEvento(evento.getEstadoEvento())
                .tipoEvento(evento.getTipoEvento())
                .activo(evento.isActivo())
                .build();
    }

    private EventoResponseDTO convertirAResponseDTO(Eventos evento) {
        return EventoResponseDTO.builder()
                .idEvento(evento.getIdEvento())
                .titulo(evento.getTitulo())
                .descripcion(evento.getDescripcion())
                .fechaEvento(evento.getFechaEvento())
                .fechaCreacion(evento.getFechaCreacion())
                .lugar(evento.getLugar())
                .tipoEvento(evento.getTipoEvento())
                .linkInscripcion(evento.getLinkInscripcion())
                .direccion(evento.getDireccion())
                .estadoEvento(evento.getEstadoEvento())
                .fechaCreacion(evento.getFechaCreacion())
                .fechaModificacion(evento.getFechaModificacion())
                .activo(evento.isActivo())
                .idEmprendimiento(evento.getEmprendimiento().getId())
                .nombreEmprendimiento(evento.getEmprendimiento().getNombreComercial())
                .idMultimedia(evento.getMultimedia().getId())
                .build();
    }
}