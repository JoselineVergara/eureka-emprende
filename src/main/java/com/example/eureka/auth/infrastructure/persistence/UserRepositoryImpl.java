package com.example.eureka.auth.infrastructure.persistence;

import com.example.eureka.auth.domain.Usuarios;
import com.example.eureka.auth.port.out.IUserRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UserRepositoryImpl implements IUserRepository {

    private final UserJpaRepository jpaRepository;

    public UserRepositoryImpl(UserJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Usuarios obtenerUsuarioCorreo(String correo) {
        return jpaRepository.obtenerUsuarioCorreo(correo);
    }

    @Override
    public Integer obtenerIdPorCorreo(String correo) {
        return jpaRepository.obtenerIdPorCorreo(correo);
    }

    @Override
    public Usuarios findByCorreo(String correo) {
        return jpaRepository.findByCorreo(correo);
    }

    @Override
    public List<Usuarios> findAllByRol_Nombre(String rolNombre) {
        return jpaRepository.findAllByRol_Nombre(rolNombre);
    }

    @Override
    public Usuarios save(Usuarios usuario) {
        return jpaRepository.save(usuario);
    }

    @Override
    public Optional<Usuarios> findById(Integer id) {
        return jpaRepository.findById(id);
    }

    @Override
    public List<Usuarios> findAll() {
        return jpaRepository.findAll();
    }

    @Override
    public void deleteById(Integer id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Integer id) {
        return jpaRepository.existsById(id);
    }
}