package com.example.eureka.auth.controller;

import com.example.eureka.auth.dto.UsuarioPerfilDTO;
import com.example.eureka.auth.service.IUsuariosService;
import com.example.eureka.model.Usuarios;
import com.example.eureka.auth.service.impl.UsuariosServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/v1/usuarios")
@RequiredArgsConstructor
public class UsuariosController {

    private final IUsuariosService usuariosServiceImpl;

    @PostMapping
    public Usuarios createUsuario(@RequestBody Usuarios usuario) {
        return usuariosServiceImpl.crearUsuario(usuario);
    }

    @GetMapping("/perfil")
    public ResponseEntity<UsuarioPerfilDTO> obtenerPerfilUsuario(Authentication authentication) {
        String email = authentication.getName();
        UsuarioPerfilDTO perfil = usuariosServiceImpl.obtenerUsuarioPorEmail(email);
        return ResponseEntity.ok(perfil);
    }

    @PutMapping("/{id}")
    public Usuarios updateUsuario(@PathVariable Integer id, @RequestBody Usuarios usuario) {
        usuario.setId(id);
        return usuariosServiceImpl.actualizarUsuario(usuario);
    }

    @PutMapping("/{id}/inactivate")
    public void inactivateUsuario(@PathVariable Integer id) {
        usuariosServiceImpl.inactivarUsuario(id);
    }

    @PutMapping("/{id}/activate")
    public void activateUsuario(@PathVariable Integer id) {
        usuariosServiceImpl.activarUsuario(id);
    }
}


