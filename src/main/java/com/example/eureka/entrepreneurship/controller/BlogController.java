package com.example.eureka.entrepreneurship.controller;

import com.example.eureka.entrepreneurship.dto.ArticuloRequestDTO;
import com.example.eureka.entrepreneurship.dto.TagDTO;
import com.example.eureka.entrepreneurship.dto.TagRequestDTO;
import com.example.eureka.entrepreneurship.service.IBlogService;
import com.example.eureka.enums.EstadoArticulo;
import com.example.eureka.utilities.SecurityUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/v1/blog")
@RequiredArgsConstructor
public class BlogController {

    private final IBlogService blogService;
    private final SecurityUtils securityUtils;  // ← Inyectar SecurityUtils

    @PostMapping("/articulos/crear")
    @PreAuthorize("hasRole('ADMINISTRADOR')")  // ← Solo administradores
    public ResponseEntity<?> crearArticulo(
            @Valid @RequestBody ArticuloRequestDTO request) {
        Integer idUsuario = securityUtils.getIdUsuarioAutenticado();
        var articulo = blogService.crearArticulo(request, idUsuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(articulo);
    }

    @PutMapping("/articulos/{idArticulo}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<?> editarArticulo(
            @PathVariable Integer idArticulo,
            @Valid @RequestBody ArticuloRequestDTO request) {
        Integer idUsuario = securityUtils.getIdUsuarioAutenticado();
        var articulo = blogService.editarArticulo(idArticulo, request, idUsuario);
        return ResponseEntity.ok(articulo);
    }

    @PutMapping("/articulos/{idArticulo}/archivar")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<?> archivarArticulo(@PathVariable Integer idArticulo) {
        Integer idUsuario = securityUtils.getIdUsuarioAutenticado();
        blogService.archivarArticulo(idArticulo, idUsuario);
        return ResponseEntity.ok("Artículo archivado exitosamente");
    }

    @PutMapping("/articulos/{idArticulo}/desarchivar")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<?> desarchivarArticulo(@PathVariable Integer idArticulo) {
        Integer idUsuario = securityUtils.getIdUsuarioAutenticado();
        blogService.desarchivarArticulo(idArticulo, idUsuario);
        return ResponseEntity.ok("Artículo desarchivado exitosamente");
    }

    @GetMapping("/articulos")
    public ResponseEntity<?> obtenerArticulos(
            @RequestParam(required = false) EstadoArticulo estado,
            @RequestParam(required = false) Integer idTag,  // ← NUEVO parámetro
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaInicio,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaFin) {
        var articulos = blogService.obtenerArticulos(estado, idTag, fechaInicio, fechaFin);
        return ResponseEntity.ok(articulos);
    }

    @GetMapping("/articulos/tag/{idTag}")
    public ResponseEntity<?> obtenerArticulosPorTag(@PathVariable Integer idTag) {
        var articulos = blogService.obtenerArticulosPorTag(idTag);
        return ResponseEntity.ok(articulos);
    }

    @GetMapping("/articulos/{idArticulo}")
    public ResponseEntity<?> obtenerArticuloPorId(@PathVariable Integer idArticulo) {
        var articulo = blogService.obtenerArticuloPorId(idArticulo);
        return ResponseEntity.ok(articulo);
    }

    @GetMapping("/tags")
    public ResponseEntity<?> obtenerTodosTags() {
        var tags = blogService.obtenerTodosTags();
        return ResponseEntity.ok(tags);
    }

    @PostMapping("/tags/crear")
    @PreAuthorize("hasRole('ADMINISTRADOR')")  // ← Solo administradores
    public ResponseEntity<?> crearTag(
            @Valid @RequestBody TagRequestDTO request) {
        Integer idUsuario = securityUtils.getIdUsuarioAutenticado();
        var tag = blogService.crearTag(request.getNombre(), idUsuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(tag);
    }
}