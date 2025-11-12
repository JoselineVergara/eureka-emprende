package com.example.eureka.auth.port.in;

import com.example.eureka.auth.infrastructure.dto.UsuarioPerfilDTO;
import com.example.eureka.auth.domain.Usuarios;
import java.util.Optional;

public interface UsuariosService {
    Usuarios crearUsuario(Usuarios usuario);
    Usuarios findByEmail(String email);
    Optional<Usuarios> obtenerUsuarioPorId(Integer id);
    UsuarioPerfilDTO obtenerUsuarioPorEmail(String email);
    Usuarios actualizarUsuario(Usuarios usuario);
    void inactivarUsuario(Integer id);
    void activarUsuario(Integer id);
}
