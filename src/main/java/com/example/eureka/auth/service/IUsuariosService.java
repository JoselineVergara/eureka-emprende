package com.example.eureka.auth.service;

import com.example.eureka.model.Usuarios;
import java.util.Optional;

public interface IUsuariosService {
    Usuarios crearUsuario(Usuarios usuario);
    Optional<Usuarios> obtenerUsuarioPorId(Integer id);
    Usuarios actualizarUsuario(Usuarios usuario);
    void inactivarUsuario(Integer id);
    void activarUsuario(Integer id);
}
