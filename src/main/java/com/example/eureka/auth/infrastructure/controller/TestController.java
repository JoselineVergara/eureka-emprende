package com.example.eureka.auth.infrastructure.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestController {

    @GetMapping("/")
    public ResponseEntity<?> test() {
        return new ResponseEntity<>("El servicio de autenticación está funcionando correctamente", HttpStatus.OK);
    }

    @GetMapping("/prueba")
    public ResponseEntity<?> test2() {
        return new ResponseEntity<>("El servicio de autenticación está funcionando correctamente", HttpStatus.OK);
    }
}
