package com.example.eureka.auth.service.impl;

import com.example.eureka.auth.repository.IUserRepository;
import com.example.eureka.auth.service.IUsuariosService;
import com.example.eureka.model.Usuarios;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class UsuariosServiceImpl implements IUsuariosService {

    private final IUserRepository usuariosRepository;

    @Override
    public Usuarios crearUsuario(Usuarios usuario) {
        usuario.setContrasena(BCrypt.hashpw(usuario.getContrasena(), BCrypt.gensalt()));
        usuario.setFechaRegistro(LocalDateTime.now());
        usuario.setActivo(true);
        return usuariosRepository.save(usuario);
    }

    @Override
    public Optional<Usuarios> obtenerUsuarioPorId(Integer id) {
        return usuariosRepository.findById(id);
    }

    @Override
    public Usuarios actualizarUsuario(Usuarios usuario) {
        return usuariosRepository.save(usuario);
    }

    @Override
    public void inactivarUsuario(Integer id) {
        usuariosRepository.findById(id).ifPresent(u -> {
            u.setActivo(false);
            usuariosRepository.save(u);
        });
    }

    @Override
    public void activarUsuario(Integer id) {
        usuariosRepository.findById(id).ifPresent(u -> {
            u.setActivo(true);
            usuariosRepository.save(u);
        });
    }
}

