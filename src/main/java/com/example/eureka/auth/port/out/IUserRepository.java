package com.example.eureka.auth.port.out;

import com.example.eureka.auth.domain.Usuarios;

import java.util.List;
import java.util.Optional;

public interface IUserRepository {

    Usuarios obtenerUsuarioCorreo(String correo);

    Integer obtenerIdPorCorreo(String correo);

    Usuarios findByCorreo(String correo);

    List<Usuarios> findAllByRol_Nombre(String rolNombre);

    Usuarios save(Usuarios usuario);

    Optional<Usuarios> findById(Integer id);

    List<Usuarios> findAll();

    void deleteById(Integer id);

    boolean existsById(Integer id);
}