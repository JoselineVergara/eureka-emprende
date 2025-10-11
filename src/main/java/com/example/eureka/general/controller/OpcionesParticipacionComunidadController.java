package com.example.eureka.general.controller;

import com.example.eureka.general.dto.OpcionesParticipacionComunidadDTO;
import com.example.eureka.general.service.IOpcionesParticipacionComunidadService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/opciones-participacion-comunidad")
@RequiredArgsConstructor
public class OpcionesParticipacionComunidadController {

    private final IOpcionesParticipacionComunidadService service;

    @GetMapping
    public List<OpcionesParticipacionComunidadDTO> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public OpcionesParticipacionComunidadDTO obtenerPorId(@PathVariable Integer id) {
        return service.obtenerPorId(id);
    }

    @PostMapping("/crear")
    public OpcionesParticipacionComunidadDTO guardar(@RequestBody OpcionesParticipacionComunidadDTO dto) {
        return service.guardar(dto);
    }

    @PutMapping("/actualizar/{id}")
    public OpcionesParticipacionComunidadDTO actualizar(@PathVariable Integer id, @RequestBody OpcionesParticipacionComunidadDTO dto) {
        return service.actualizar(id, dto);
    }

    @DeleteMapping("/eliminar/{id}")
    public void eliminar(@PathVariable Integer id) {
        service.eliminar(id);
    }
}
