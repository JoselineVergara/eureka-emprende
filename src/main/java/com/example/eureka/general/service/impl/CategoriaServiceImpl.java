package com.example.eureka.general.service.impl;

import com.example.eureka.general.dto.CategoriasDTO;
import com.example.eureka.general.repository.ICategoriasRepository;
import com.example.eureka.general.repository.IMultimediaRepository;
import com.example.eureka.general.service.ICategoriaService;
import com.example.eureka.model.Categorias;
import com.example.eureka.model.Multimedia;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoriaServiceImpl implements ICategoriaService {

    private final ICategoriasRepository categoriasRepository;
    private final IMultimediaRepository multimediaRepository;

    @Override
    @Transactional
    public Integer crearCategoria(CategoriasDTO categoriasDTO) {
        if (categoriasDTO == null) {
            throw new IllegalArgumentException("El DTO de categoría no puede ser nulo");
        }

        Categorias categoria = new Categorias();
        categoria.setNombre(categoriasDTO.getNombre());
        categoria.setDescripcion(categoriasDTO.getDescripcion());

        if (categoriasDTO.getIdMultimedia() != null) {
            Multimedia multimedia = multimediaRepository.findById(categoriasDTO.getIdMultimedia())
                    .orElseThrow(() -> new EntityNotFoundException(
                            "No se encontró la multimedia con ID: " + categoriasDTO.getIdMultimedia()));
            categoria.setMultimedia(multimedia);
        }

        Categorias model = categoriasRepository.save(categoria);
        return model.getId();
    }

    @Override
    @Transactional
    public Integer actualizarCategoria(Integer id, CategoriasDTO categoriasDTO) {
        Categorias categoria = categoriasRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Categoría no encontrada con ID: " + id));

        categoria.setNombre(categoriasDTO.getNombre());
        categoria.setDescripcion(categoriasDTO.getDescripcion());

        if (categoriasDTO.getIdMultimedia() != null) {
            Multimedia multimedia = multimediaRepository.findById(categoriasDTO.getIdMultimedia())
                    .orElseThrow(() -> new EntityNotFoundException(
                            "No se encontró la multimedia con ID: " + categoriasDTO.getIdMultimedia()));
            categoria.setMultimedia(multimedia);
        } else {
            categoria.setMultimedia(null);
        }

        categoriasRepository.save(categoria);
        return id;
    }

    @Override
    @Transactional
    public void eliminarCategoria(Integer id) {
        if (!categoriasRepository.existsById(id)) {
            throw new EntityNotFoundException("No existe una categoría con el ID: " + id);
        }
        categoriasRepository.deleteById(id);
    }

    @Override
    public List<CategoriasDTO> obtenerCategorias() {
        return categoriasRepository.findAll().stream()
                .map(this::getCategoriasDTO)
                .toList();
    }

    private CategoriasDTO getCategoriasDTO(Categorias categoria) {
        CategoriasDTO dto = new CategoriasDTO();
        dto.setNombre(categoria.getNombre());
        dto.setDescripcion(categoria.getDescripcion());
        if (categoria.getMultimedia() != null) {
            dto.setUrlImagen(categoria.getMultimedia().getUrlArchivo());
            dto.setIdMultimedia(categoria.getMultimedia().getId());
        }
        return dto;
    }

    @Override
    public CategoriasDTO obtenerCategoriaPorId(Integer id) {
        Categorias categoria = categoriasRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Categoría no encontrada con ID: " + id));

        return getCategoriasDTO(categoria);
    }
}
