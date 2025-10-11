package com.example.eureka.general.controller;

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
        List<CategoriasDTO> categorias = categoriaService.obtenerCategorias();
        return ResponseEntity.ok(categorias);
    }

    @PostMapping("/crear")
    public ResponseEntity<?> crearCategoria(@RequestBody CategoriasDTO categoriasDTO) {
        Integer id = categoriaService.crearCategoria(categoriasDTO);
        return ResponseEntity.ok(id);
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<?> actualizarCategoria(@PathVariable Integer id, @RequestBody CategoriasDTO categoriasDTO) {
        categoriaService.actualizarCategoria(id, categoriasDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminarCategoria(@PathVariable Integer id) {
        categoriaService.eliminarCategoria(id);
        return ResponseEntity.ok().build();
    }
}
