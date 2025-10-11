package com.example.eureka.entrepreneurship.service.impl;

import com.example.eureka.config.BusinessException;
import com.example.eureka.entrepreneurship.dto.ArticuloRequestDTO;
import com.example.eureka.entrepreneurship.dto.ArticuloResponseDTO;
import com.example.eureka.entrepreneurship.dto.TagDTO;
import com.example.eureka.enums.EstadoArticulo;
import com.example.eureka.model.ArticulosBlog;
import com.example.eureka.model.Multimedia;
import com.example.eureka.model.TagsBlog;
import com.example.eureka.model.Usuarios;
import com.example.eureka.entrepreneurship.repository.IArticuloRepository;
import com.example.eureka.entrepreneurship.repository.ITagRepository;
import com.example.eureka.entrepreneurship.service.IBlogService;
import com.example.eureka.general.repository.IMultimediaRepository;
import com.example.eureka.auth.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BlogServiceImpl implements IBlogService {

    private final IArticuloRepository articuloRepository;
    private final ITagRepository tagRepository;
    private final IMultimediaRepository multimediaRepository;
    private final IUserRepository userRepository;

    @Transactional
    @Override
    public ArticuloResponseDTO crearArticulo(ArticuloRequestDTO request, Integer idUsuario) {
        // Validar que solo administrador puede crear artículos
        validarAdministrador(idUsuario);

        Usuarios usuario = userRepository.findById(idUsuario.intValue())
                .orElseThrow(() -> new BusinessException("Usuario no encontrado"));

        Multimedia imagen = multimediaRepository.findById(request.getIdImagen())
                .orElseThrow(() -> new BusinessException("Imagen no encontrada"));

        ArticulosBlog articulo = new ArticulosBlog();
        articulo.setTitulo(request.getTitulo());
        articulo.setDescripcionCorta(request.getDescripcionCorta());
        articulo.setContenido(request.getContenido());

        // NUEVO: Usar el estado recibido en el request, si no viene asumimos BORRADOR
        articulo.setEstado(request.getEstado() != null ? request.getEstado() : EstadoArticulo.BORRADOR);

        articulo.setFechaCreacion(LocalDateTime.now());
        articulo.setImagen(imagen);
        articulo.setUsuario(usuario);

        // Agregar tags existentes
        if (request.getIdsTags() != null && !request.getIdsTags().isEmpty()) {
            for (Integer idTag : request.getIdsTags()) {
                TagsBlog tag = tagRepository.findById(idTag)
                        .orElseThrow(() -> new BusinessException("Tag no encontrado: " + idTag));
                articulo.getTags().add(tag);
            }
        }

        // Crear nuevos tags si es necesario
        if (request.getNombresTags() != null && !request.getNombresTags().isEmpty()) {
            for (String nombreTag : request.getNombresTags()) {
                TagsBlog tag = tagRepository.findByNombre(nombreTag)
                        .orElseGet(() -> {
                            TagsBlog nuevoTag = new TagsBlog();
                            nuevoTag.setNombre(nombreTag);
                            return tagRepository.save(nuevoTag);
                        });
                articulo.getTags().add(tag);
            }
        }

        ArticulosBlog articuloGuardado = articuloRepository.save(articulo);
        return convertirADTO(articuloGuardado);
    }

    @Transactional
    @Override
    public ArticuloResponseDTO editarArticulo(Integer idArticulo, ArticuloRequestDTO request, Integer idUsuario) {
        validarAdministrador(idUsuario);

        ArticulosBlog articulo = articuloRepository.findById(idArticulo)
                .orElseThrow(() -> new BusinessException("Artículo no encontrado"));

        if (articulo.getEstado() == EstadoArticulo.ARCHIVADO) {
            throw new BusinessException("No se puede editar un artículo archivado");
        }

        articulo.setTitulo(request.getTitulo());
        articulo.setDescripcionCorta(request.getDescripcionCorta());
        articulo.setContenido(request.getContenido());
        articulo.setEstado(request.getEstado()); // AGREGAR ESTA LÍNEA
        articulo.setFechaModificacion(LocalDateTime.now());

        // Actualizar imagen si es diferente
        if (!articulo.getImagen().getId().equals(request.getIdImagen())) {
            Multimedia imagen = multimediaRepository.findById(request.getIdImagen())
                    .orElseThrow(() -> new BusinessException("Imagen no encontrada"));
            articulo.setImagen(imagen);
        }

        // Actualizar tags
        articulo.getTags().clear();

        if (request.getIdsTags() != null && !request.getIdsTags().isEmpty()) {
            for (Integer idTag : request.getIdsTags()) {
                TagsBlog tag = tagRepository.findById(idTag)
                        .orElseThrow(() -> new BusinessException("Tag no encontrado: " + idTag));
                articulo.getTags().add(tag);
            }
        }

        if (request.getNombresTags() != null && !request.getNombresTags().isEmpty()) {
            for (String nombreTag : request.getNombresTags()) {
                TagsBlog tag = tagRepository.findByNombre(nombreTag)
                        .orElseGet(() -> {
                            TagsBlog nuevoTag = new TagsBlog();
                            nuevoTag.setNombre(nombreTag);
                            return tagRepository.save(nuevoTag);
                        });
                articulo.getTags().add(tag);
            }
        }

        ArticulosBlog articuloActualizado = articuloRepository.save(articulo);
        return convertirADTO(articuloActualizado);
    }

    @Transactional(readOnly = true)
    public List<ArticuloResponseDTO> obtenerArticulos(EstadoArticulo estado, LocalDateTime fechaInicio,
                                                      LocalDateTime fechaFin) {
        List<ArticulosBlog> articulos = articuloRepository.findAll();

        // Filtrar por estado
        if (estado != null) {
            articulos = articulos.stream()
                    .filter(a -> a.getEstado() == estado)
                    .collect(Collectors.toList());
        }

        // Filtrar por rango de fechas
        if (fechaInicio != null && fechaFin != null) {
            articulos = articulos.stream()
                    .filter(a -> a.getFechaCreacion().isAfter(fechaInicio) &&
                            a.getFechaCreacion().isBefore(fechaFin))
                    .collect(Collectors.toList());
        } else if (fechaInicio != null) {
            articulos = articulos.stream()
                    .filter(a -> a.getFechaCreacion().isAfter(fechaInicio))
                    .collect(Collectors.toList());
        } else if (fechaFin != null) {
            articulos = articulos.stream()
                    .filter(a -> a.getFechaCreacion().isBefore(fechaFin))
                    .collect(Collectors.toList());
        }

        // Ordenar siempre por fecha de creación (descendente)
        articulos.sort((a, b) -> b.getFechaCreacion().compareTo(a.getFechaCreacion()));

        return articulos.stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }


    @Transactional
    @Override
    public void archivarArticulo(Integer idArticulo, Integer idUsuario) {
        // Validar que solo administrador puede archivar artículos
        validarAdministrador(idUsuario);

        ArticulosBlog articulo = articuloRepository.findById(idArticulo)
                .orElseThrow(() -> new BusinessException("Artículo no encontrado"));

        articulo.setEstado(EstadoArticulo.ARCHIVADO);
        articulo.setFechaModificacion(LocalDateTime.now());
        articuloRepository.save(articulo);
    }

    @Transactional
    @Override
    public void desarchivarArticulo(Integer idArticulo, Integer idUsuario) {
        validarAdministrador(idUsuario);

        ArticulosBlog articulo = articuloRepository.findById(idArticulo)
                .orElseThrow(() -> new BusinessException("Artículo no encontrado"));

        if (articulo.getEstado() != EstadoArticulo.ARCHIVADO) {
            throw new BusinessException("El artículo no está archivado");
        }

        articulo.setEstado(EstadoArticulo.PUBLICADO);
        articulo.setFechaModificacion(LocalDateTime.now());
        articuloRepository.save(articulo);
    }
    

    @Transactional(readOnly = true)
    @Override
    public List<ArticuloResponseDTO> obtenerArticulosPorTag(Integer idTag) {
        List<ArticulosBlog> articulos = articuloRepository.findByTagId(idTag);
        return articulos.stream()
                .filter(a -> a.getEstado() == EstadoArticulo.PUBLICADO)
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public ArticuloResponseDTO obtenerArticuloPorId(Integer idArticulo) {
        ArticulosBlog articulo = articuloRepository.findById(idArticulo)
                .orElseThrow(() -> new BusinessException("Artículo no encontrado"));
        return convertirADTO(articulo);
    }


    @Transactional(readOnly = true)
    @Override
    public List<TagDTO> obtenerTodosTags() {
        List<TagsBlog> tags = tagRepository.findAll();
        return tags.stream()
                .map(tag -> TagDTO.builder()
                        .idTag(tag.getIdTag())
                        .nombre(tag.getNombre())
                        .build())
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public TagDTO crearTag(String nombre, Integer idUsuario) {
        // Validar que solo administrador puede crear tags
        validarAdministrador(idUsuario);

        if (tagRepository.findByNombre(nombre).isPresent()) {
            throw new BusinessException("El tag ya existe");
        }

        TagsBlog tag = new TagsBlog();
        tag.setNombre(nombre);
        TagsBlog tagGuardado = tagRepository.save(tag);

        return TagDTO.builder()
                .idTag(tagGuardado.getIdTag())
                .nombre(tagGuardado.getNombre())
                .build();
    }

    // NUEVO: Método para validar rol de administrador
    private void validarAdministrador(Integer idUsuario) {
        if (idUsuario == null) {
            throw new BusinessException("Usuario no especificado");
        }

        Usuarios usuario = userRepository.findById(idUsuario.intValue())
                .orElseThrow(() -> new BusinessException("Usuario no encontrado"));

        // Validar que el rol sea Administrador
        if (usuario.getRol() == null || !usuario.getRol().getNombre().equalsIgnoreCase("Administrador")) {
            throw new BusinessException("Solo los administradores pueden realizar esta acción");
        }
    }

    private ArticuloResponseDTO convertirADTO(ArticulosBlog articulo) {
        List<TagDTO> tags = articulo.getTags().stream()
                .map(tag -> TagDTO.builder()
                        .idTag(tag.getIdTag())
                        .nombre(tag.getNombre())
                        .build())
                .collect(Collectors.toList());

        return ArticuloResponseDTO.builder()
                .idArticulo(articulo.getIdArticulo())
                .titulo(articulo.getTitulo())
                .descripcionCorta(articulo.getDescripcionCorta())
                .contenido(articulo.getContenido())
                .estado(articulo.getEstado())
                .fechaCreacion(articulo.getFechaCreacion())
                .fechaModificacion(articulo.getFechaModificacion())
                .idImagen(articulo.getImagen().getId())
                .urlImagen(articulo.getImagen().getUrlArchivo())
                .idUsuario(articulo.getUsuario().getId())
                .nombreUsuario(articulo.getUsuario().getNombre() + " " + articulo.getUsuario().getApellido())
                .tags(tags)
                .build();
    }
}
