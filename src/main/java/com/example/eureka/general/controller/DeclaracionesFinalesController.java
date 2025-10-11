package com.example.eureka.general.controller;

import com.example.eureka.general.dto.DeclaracionesFinalesDTO;
import com.example.eureka.general.service.IDeclaracionesFinalesService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/declaraciones-finales")
@RequiredArgsConstructor
public class DeclaracionesFinalesController {

    private final IDeclaracionesFinalesService service;

    @GetMapping
    public List<DeclaracionesFinalesDTO> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public DeclaracionesFinalesDTO obtenerPorId(@PathVariable Integer id) {
        return service.obtenerPorId(id);
    }

    @PostMapping("/crear")
    public DeclaracionesFinalesDTO guardar(@RequestBody DeclaracionesFinalesDTO dto) {
        return service.guardar(dto);
    }

    @PutMapping("/actualizar/{id}")
    public DeclaracionesFinalesDTO actualizar(@PathVariable Integer id, @RequestBody DeclaracionesFinalesDTO dto) {
        return service.actualizar(id, dto);
    }

    @DeleteMapping("/eliminar/{id}")
    public void eliminar(@PathVariable Integer id) {
        service.eliminar(id);
    }
}
