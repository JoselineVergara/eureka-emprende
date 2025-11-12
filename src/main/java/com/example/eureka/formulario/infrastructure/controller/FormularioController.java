package com.example.eureka.formulario.infrastructure.controller;

import com.example.eureka.formulario.infrastructure.dto.response.FormularioResponseDTO;
import com.example.eureka.formulario.port.in.FormularioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/formularios")
public class FormularioController {

    private final FormularioService formularioService;

    public FormularioController(FormularioService formularioService) {
        this.formularioService = formularioService;
    }

    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<FormularioResponseDTO> getFormularioByTipo(@PathVariable String tipo) {
        FormularioResponseDTO formulario = formularioService.getFormularioByTipo(tipo);
        return ResponseEntity.ok(formulario);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FormularioResponseDTO> getFormularioById(@PathVariable Long id) {
        FormularioResponseDTO formulario = formularioService.getFormularioById(id);
        return ResponseEntity.ok(formulario);
    }

}