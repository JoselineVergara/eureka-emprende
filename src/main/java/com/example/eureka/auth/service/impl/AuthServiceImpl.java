package com.example.eureka.auth.service.impl;

import com.example.eureka.auth.dto.UsuarioEmprendeDTO;
import com.example.eureka.auth.repository.IUserRepository;
import com.example.eureka.auth.service.IAuthService;
import com.example.eureka.entrepreneurship.repository.IEmprendimientosRepository;
import com.example.eureka.entrepreneurship.repository.ITiposEmprendimientoRepository;
import com.example.eureka.enums.EstadoEmprendimiento;
import com.example.eureka.general.repository.ICiudadesRepository;
import com.example.eureka.general.repository.IRolesRepository;
import com.example.eureka.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements IAuthService {

    private final IUserRepository userRepository;

    private final IRolesRepository rolesRepository;

    private final ICiudadesRepository ciudadesRepository;

    private final IEmprendimientosRepository  emprendimientosRepository;

    private final ITiposEmprendimientoRepository tiposEmprendimientoRepository;

    @Override
    @Transactional
    public Usuarios createUser(UsuarioEmprendeDTO usuario) throws Exception {

        if (usuario.getNombre().isEmpty() || usuario.getCorreo().isEmpty() || usuario.getContrasena().isEmpty())
            throw new Exception("nombre y correo son requeridos");

        Usuarios existingUsuarios = userRepository.findByCorreo(usuario.getCorreo());
        if (existingUsuarios != null)
            throw new Exception("El correo " + usuario.getCorreo() + " ya se encuentra en uso");

        TiposEmprendimientos tiposEmprendimientos = tiposEmprendimientoRepository.findById(usuario.getTipoEmprendimiento())
                .orElseThrow(() -> new Exception("Tipo de emprendimiento no encontrado"));

        Roles rol = rolesRepository.findById(2)
                .orElseThrow(() -> new Exception("Rol no encontrado"));

        Ciudades ciudad = ciudadesRepository.findById(usuario.getCiudad())
                .orElseThrow(() -> new Exception("Ciudad no encontrada"));

        // Creación de usuario
        Usuarios newUsuarios = new Usuarios();
        newUsuarios.setNombre(usuario.getNombre().toLowerCase());
        newUsuarios.setApellido(usuario.getApellido().toLowerCase());
        newUsuarios.setCorreo(usuario.getCorreo().toLowerCase());
        newUsuarios.setContrasena(BCrypt.hashpw(usuario.getContrasena(), BCrypt.gensalt()));
        newUsuarios.setGenero(usuario.getGenero().toLowerCase());
        newUsuarios.setFechaRegistro(new Date());
        newUsuarios.setRol(rol);
        newUsuarios.setFechaNacimiento(usuario.getFechaNacimiento());
        newUsuarios.setActivo(true);
        Usuarios modelUsuario = userRepository.save(newUsuarios);

        // Creación básica del emprendimiento
        Emprendimientos emprendimientoBasico = new Emprendimientos();
        emprendimientoBasico.setNombreComercial(usuario.getNombreComercialEmprendimiento());
        emprendimientoBasico.setAnioCreacion(usuario.getFechaCreacion());
        emprendimientoBasico.setActivoEmprendimiento(usuario.getEstadoEmprendimiento());
        emprendimientoBasico.setFechaCreacion(usuario.getFechaCreacion());
        emprendimientoBasico.setEstadoEmprendimiento(String.valueOf(EstadoEmprendimiento.BORRADOR));
        emprendimientoBasico.setUsuarios(modelUsuario);
        emprendimientoBasico.setCiudades(ciudad);
        emprendimientoBasico.setTiposEmprendimientos(tiposEmprendimientos);

        emprendimientosRepository.save(emprendimientoBasico);

        return modelUsuario;
    }

}
