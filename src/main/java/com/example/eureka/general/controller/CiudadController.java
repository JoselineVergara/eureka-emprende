package com.example.eureka.general.controller;

import com.example.eureka.general.dto.CiudadDTO;
import com.example.eureka.general.service.ICiudadService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/ciudad")
@RequiredArgsConstructor
public class CiudadController {

    private final ICiudadService ciudadService;

    @GetMapping
    public List<CiudadDTO> listar() {
        return ciudadService.listar();
    }

    @GetMapping("/{id}")
    public CiudadDTO obtenerPorId(@PathVariable Integer id) {
        return ciudadService.obtenerPorId(id);
    }

    @PostMapping("/crear")
    public CiudadDTO guardar(@RequestBody CiudadDTO dto) {
        return ciudadService.guardar(dto);
    }

    @PutMapping("/actualizar/{id}")
    public CiudadDTO actualizar(@PathVariable Integer id, @RequestBody CiudadDTO dto) {
        return ciudadService.actualizar(id, dto);
    }

    @DeleteMapping("/eliminar/{id}")
    public void eliminar(@PathVariable Integer id) {
        ciudadService.eliminar(id);
    }
}
