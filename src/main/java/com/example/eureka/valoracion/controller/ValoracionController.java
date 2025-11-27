package com.example.eureka.valoracion.controller;

import com.example.eureka.domain.model.Emprendimientos;
import com.example.eureka.valoracion.dto.EmprendimientoInfo;
import com.example.eureka.valoracion.dto.RespuestaFormularioDTO;
import com.example.eureka.valoracion.service.ValoracionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/v1/valoracion")
@RequiredArgsConstructor
public class ValoracionController {

    private final ValoracionService valoracionService;


    @GetMapping("/emprendimientos")
    public ResponseEntity<List<EmprendimientoInfo>> obtenerEmprendimientos(){
        return ResponseEntity.ok(valoracionService.obtenerEmprendimientos());
    }

    @GetMapping("/respuesta-emprendimiento")
    public ResponseEntity<HashMap<String, Object>>  obtenerRespuestaEmprendimiento(@RequestBody Emprendimientos emprendimientos){
        boolean valida = valoracionService.existsByEmprendimientos(emprendimientos);
        HashMap<String, Object> respuesta = new HashMap<>();
        if(valida){
            List<RespuestaFormularioDTO> res = valoracionService.obtenerRespuestasPorEmprendimiento(emprendimientos.getId().longValue());
            respuesta.put("status", 200);
            respuesta.put("message", res);
            return ResponseEntity.ok(respuesta);
        }else{
            respuesta.put("status", 203);
            respuesta.put("message","No hay respuestas del emprendimiento");
            return ResponseEntity.ok(respuesta);
        }

    }

}
