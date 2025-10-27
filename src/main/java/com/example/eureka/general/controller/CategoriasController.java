package com.example.eureka.general.controller;

import com.example.eureka.general.dto.CategoriaRequestDTO;
import com.example.eureka.general.dto.CategoriasDTO;
import com.example.eureka.general.service.ICategoriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/categorias")
@RequiredArgsConstructor
public class CategoriasController {

    private final ICategoriaService categoriaService;

    @GetMapping
    public ResponseEntity<List<CategoriasDTO>> obtenerCategorias() {
        List<CategoriasDTO> categorias = categoriaService.listarCategoaria();
        return ResponseEntity.ok(categorias);
    }

    @PostMapping("/crear")
    public ResponseEntity<?> crearCategoria(@RequestBody CategoriaRequestDTO categoriasDTO) {
        CategoriasDTO dto = categoriaService.crearCategoria(categoriasDTO);
        return ResponseEntity.ok(dto);
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<?> actualizarCategoria(@PathVariable Integer id, @RequestBody CategoriaRequestDTO categoriasDTO) {
        categoriaService.actualizarCategoaria(id, categoriasDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminarCategoria(@PathVariable Integer id) {
        categoriaService.eliminarCategoria(id);
        return ResponseEntity.ok().build();
    }
}
