package com.example.eureka.articulo.aplication.service;

import com.example.eureka.articulo.domain.model.ArticulosBlog;
import com.example.eureka.articulo.domain.model.TagsBlog;
import com.example.eureka.articulo.infrastructure.dto.response.TagDTO;
import com.example.eureka.articulo.port.in.ArticuloTagService;
import com.example.eureka.articulo.port.out.ITagRepository;
import com.example.eureka.auth.domain.Usuarios;
import com.example.eureka.auth.port.out.IUserRepository;
import com.example.eureka.shared.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ArticuloTagServiceImpl implements ArticuloTagService {

    private final ITagRepository tagRepository;
    private final IUserRepository userRepository;

    @Override
    @Transactional
    public void procesarTags(ArticulosBlog articulo, List<Integer> idsTags, List<String> nombresTags) {
        if (idsTags != null && !idsTags.isEmpty()) {
            for (Integer idTag : idsTags) {
                TagsBlog tag = tagRepository.findById(idTag)
                        .orElseThrow(() -> new BusinessException("Tag no encontrado: " + idTag));
                articulo.getTags().add(tag);
            }
        }

        if (nombresTags != null && !nombresTags.isEmpty()) {
            List<String> nombresDistinct = nombresTags.stream()
                    .filter(nombre -> nombre != null && !nombre.trim().isEmpty())
                    .map(nombre -> nombre.trim().toLowerCase())
                    .distinct()
                    .collect(Collectors.toList());

            List<TagsBlog> existentes = tagRepository.findAllByNombreInIgnoreCase(nombresDistinct);

            var mapaExistentes = existentes.stream()
                    .collect(Collectors.toMap(TagsBlog::getNombre, tag -> tag));

            for (String nombreTag : nombresDistinct) {
                TagsBlog tag = mapaExistentes.get(nombreTag);
                if (tag == null) {
                    TagsBlog nuevoTag = new TagsBlog();
                    nuevoTag.setNombre(nombreTag);  // Siempre en minúsculas
                    tag = tagRepository.save(nuevoTag);
                    mapaExistentes.put(nombreTag, tag);
                }
                articulo.getTags().add(tag);
            }
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<TagDTO> obtenerTodosTags() {
        List<TagsBlog> tags = tagRepository.findAll();
        return convertirTags(tags);
    }

    @Override
    @Transactional
    public TagDTO crearTag(String nombre, Integer idUsuario) {
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

    private List<TagDTO> convertirTags(List<TagsBlog> tags) {
        return tags.stream()
                .map(tag -> TagDTO.builder()
                        .idTag(tag.getIdTag())
                        .nombre(tag.getNombre())
                        .build())
                .collect(Collectors.toList());
    }

    private void validarAdministrador(Integer idUsuario) {
        if (idUsuario == null) {
            throw new BusinessException("Usuario no especificado");
        }

        Usuarios usuario = userRepository.findById(idUsuario)
                .orElseThrow(() -> new BusinessException("Usuario no encontrado"));

        if (usuario.getRol() == null || !usuario.getRol().getNombre().equalsIgnoreCase("Administrador")) {
            throw new BusinessException("Solo los administradores pueden realizar esta acción");
        }
    }
}
