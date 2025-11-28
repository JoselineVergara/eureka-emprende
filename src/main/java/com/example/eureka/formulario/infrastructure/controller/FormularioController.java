package com.example.eureka.formulario.infrastructure.controller;

import com.example.eureka.autoevaluacion.service.AutoevaluacionService;
import com.example.eureka.domain.model.OpcionRespuesta;
import com.example.eureka.domain.model.Respuesta;
import com.example.eureka.formulario.infrastructure.dto.response.FormularioResponseDTO;
import com.example.eureka.formulario.infrastructure.dto.response.OpcionRespuestaDTO;
import com.example.eureka.formulario.port.in.FormularioService;
import com.example.eureka.formulario.port.in.OpcionRespuestaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/formularios")
public class FormularioController {

    private final FormularioService formularioService;
    private final OpcionRespuestaService opcionRespuestaService;
    private final AutoevaluacionService respuestaService;


    public FormularioController(FormularioService formularioService, OpcionRespuestaService opcionRespuestaService, AutoevaluacionService respuestaService) {
        this.formularioService = formularioService;
        this.opcionRespuestaService = opcionRespuestaService;
        this.respuestaService = respuestaService;
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

    @PostMapping("/save-opcion-respuesta")
    public ResponseEntity<OpcionRespuestaDTO> save(@RequestBody OpcionRespuesta opcionRespuestaDTO) {
        return ResponseEntity.ok(opcionRespuestaService.save(opcionRespuestaDTO));
    }

    @GetMapping("/get-respuesta/{idRespuesta}")
    public ResponseEntity<List<OpcionRespuestaDTO>> findAllByRespuesta(@PathVariable Long idRespuesta) {
        Respuesta respuesta = respuestaService.findById(idRespuesta);
        return ResponseEntity.ok(opcionRespuestaService.findAllByRespuesta(respuesta));
    }



}