package com.example.eureka.general.controller;

import com.example.eureka.general.dto.DescripcionesDTO;
import com.example.eureka.general.service.DescripcionesService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/descripciones")
@RequiredArgsConstructor
public class DescripcionesController {

    private final DescripcionesService service;

    @GetMapping
    public List<DescripcionesDTO> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public DescripcionesDTO obtenerPorId(@PathVariable Integer id) {
        return service.obtenerPorId(id);
    }

    @PostMapping("/crear")
    public DescripcionesDTO guardar(@RequestBody DescripcionesDTO dto) {
        return service.guardar(dto);
    }

    @PutMapping("/actualizar/{id}")
    public DescripcionesDTO actualizar(@PathVariable Integer id, @RequestBody DescripcionesDTO dto) {
        return service.actualizar(id, dto);
    }

    @DeleteMapping("/eliminar/{id}")
    public void eliminar(@PathVariable Integer id) {
        service.eliminar(id);
    }

}
