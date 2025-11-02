package com.example.eureka.entrepreneurship.controller;

import com.example.eureka.entrepreneurship.dto.SolicitudEmprendimientoDTO;
import com.example.eureka.entrepreneurship.service.ISolicitudEmprendimientoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/solicitudes-emprendimiento")
@RequiredArgsConstructor
public class SolicitudEmprendimientoController {

    private final ISolicitudEmprendimientoService service;

    @PostMapping
    public SolicitudEmprendimientoDTO crear(@RequestBody SolicitudEmprendimientoDTO dto) {
        return service.crear(dto);
    }

    @GetMapping("/{id}")
    public SolicitudEmprendimientoDTO obtenerPorId(@PathVariable Integer id) {
        return service.obtenerPorId(id);
    }

    @GetMapping
    public List<SolicitudEmprendimientoDTO> obtenerTodos() {
        return service.obtenerTodos();
    }

    @PutMapping("/{id}")
    public SolicitudEmprendimientoDTO actualizar(@PathVariable Integer id, @RequestBody SolicitudEmprendimientoDTO dto) {
        return service.actualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Integer id) {
        service.eliminar(id);
    }
}

