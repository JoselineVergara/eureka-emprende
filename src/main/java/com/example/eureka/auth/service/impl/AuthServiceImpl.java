package com.example.eureka.auth.service.impl;

import com.example.eureka.auth.dto.UsuarioEmprendeDTO;
import com.example.eureka.general.repository.IRolesRepository;
import com.example.eureka.model.Roles;
import com.example.eureka.model.Usuarios;
import com.example.eureka.auth.repository.IUserRepository;
import com.example.eureka.auth.service.IAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements IAuthService {

    private final IUserRepository userRepository;

    private final IRolesRepository rolesRepository;

    @Override
    @Transactional
    public Usuarios createUser(UsuarioEmprendeDTO usuario) throws Exception {

        if (usuario.getNombre().isEmpty() || usuario.getCorreo().isEmpty() || usuario.getContrasena().isEmpty()) throw new Exception("name,email and password are required");

        Usuarios existingUsuarios = userRepository.findUsuariosByCorreo(usuario.getCorreo());
        if (existingUsuarios != null) throw new Exception("Email " + usuario.getCorreo() + " is already in use");

        Optional<Roles> rol = rolesRepository.findById(usuario.getIdRol());

        if (rol.isEmpty()) throw new Exception("Role with id " + usuario.getIdRol() + " not found");

        //creacion de usuario
        Usuarios newUsuarios = new Usuarios();
        newUsuarios.setNombre(usuario.getNombre().toLowerCase());
        newUsuarios.setApellido(usuario.getApellido().toLowerCase());
        newUsuarios.setCorreo(usuario.getCorreo().toLowerCase());
        newUsuarios.setContrasena( BCrypt.hashpw(usuario.getContrasena(), BCrypt.gensalt()));
        newUsuarios.setGenero(usuario.getGenero().toLowerCase());
        newUsuarios.setFechaRegistro(new Date());
        newUsuarios.setRol(rol.get());
        newUsuarios.setFechaNacimiento(usuario.getFechaNacimiento());
        newUsuarios.setActivo(true);

        //


        return userRepository.save(newUsuarios);
    }
}
