package com.example.eureka.auth.aplication.services;

import com.example.eureka.auth.infrastructure.dto.UsuarioPerfilDTO;
import com.example.eureka.auth.infrastructure.dto.converter.UsuarioConverter;
import com.example.eureka.auth.port.out.IUserRepository;
import com.example.eureka.auth.port.in.UsuariosService;
import com.example.eureka.auth.domain.Usuarios;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class UsuariosServiceImpl implements UsuariosService {

    private final IUserRepository usuariosRepository;
    private final UsuarioConverter usuarioConverter;

    @Override
    public Usuarios crearUsuario(Usuarios usuario) {
        usuario.setContrasena(BCrypt.hashpw(usuario.getContrasena(), BCrypt.gensalt()));
        usuario.setFechaRegistro(LocalDateTime.now());
        usuario.setActivo(true);
        return usuariosRepository.save(usuario);
    }
    @Transactional(readOnly = true)
    public UsuarioPerfilDTO obtenerUsuarioPorEmail(String email) {
        Usuarios usuario = usuariosRepository.findByCorreo(email);
//                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con email: " + email));

        return usuarioConverter.toPerfilDTO(usuario);
    }

    @Override
    public Optional<Usuarios> obtenerUsuarioPorId(Integer id) {
        return usuariosRepository.findById(id);
    }

    @Override
    public Usuarios findByEmail(String email) {
        return usuariosRepository.findByCorreo(email);
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

