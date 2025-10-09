package com.example.eureka.entrepreneurship.controller;

import com.example.eureka.entrepreneurship.dto.EmprendimientoRequestDTO;
import com.example.eureka.entrepreneurship.service.IEmprendimientoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/emprendimientos")
@RequiredArgsConstructor
public class EmprendimientoController {

    private final IEmprendimientoService emprendimientoService;

    @GetMapping
    public ResponseEntity<?> obtenerEmprendimientos() {
        var lista = emprendimientoService.obtenerEmprendimientos();
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerEmprendimientoPorId(@PathVariable Integer id) {
        var emprendimiento = emprendimientoService.obtenerEmprendimientoPorId(id);
        return ResponseEntity.ok(emprendimiento);
    }

    @PostMapping("/crear")
    public ResponseEntity<?> crearEmprendimiento(@RequestBody EmprendimientoRequestDTO dto) throws Exception {
        emprendimientoService.estructuraEmprendimiento(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
