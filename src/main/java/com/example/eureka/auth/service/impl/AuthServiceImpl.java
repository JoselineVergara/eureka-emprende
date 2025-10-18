package com.example.eureka.auth.service.impl;

import com.example.eureka.auth.dto.UsuarioEmprendeDTO;
import com.example.eureka.auth.repository.IUserRepository;
import com.example.eureka.auth.service.IAuthService;
import com.example.eureka.entrepreneurship.repository.IRepresentanteInformacionRepository;
import com.example.eureka.entrepreneurship.service.IEmprendimientoService;
import com.example.eureka.enums.Genero;
import com.example.eureka.general.repository.IRolesRepository;
import com.example.eureka.model.Emprendimientos;
import com.example.eureka.model.InformacionRepresentante;
import com.example.eureka.model.Roles;
import com.example.eureka.model.Usuarios;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.expression.ExpressionException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Date;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthServiceImpl implements IAuthService {

    private final IUserRepository userRepository;
    private final IRolesRepository rolesRepository;
    private final IEmprendimientoService emprendimientoService;
    private final IRepresentanteInformacionRepository informacionRepresentanteRepository;

    @Override
    public Usuarios createUser(@Valid UsuarioEmprendeDTO usuario) {

        // Validar correo duplicado
        Usuarios existingUser = userRepository.findByCorreo(usuario.getCorreo());
        if (existingUser != null) {
            throw new ExpressionException("El correo " + usuario.getCorreo() + " ya está en uso");
        }

        // Obtener el rol
        Roles rol = rolesRepository.findById(usuario.getIdRol())
                .orElseThrow(() -> new ExpressionException("Rol no encontrado"));

        // Crear nuevo usuario
        Usuarios newUsuario = new Usuarios();
        newUsuario.setNombre(usuario.getNombre());
        newUsuario.setApellido(usuario.getApellido());
        newUsuario.setCorreo(usuario.getCorreo().toLowerCase());
        newUsuario.setContrasena(BCrypt.hashpw(usuario.getContrasena(), BCrypt.gensalt()));
        newUsuario.setGenero(Genero.valueOf(usuario.getGenero()));
        newUsuario.setFechaRegistro(LocalDateTime.now());
        newUsuario.setFechaNacimiento(usuario.getFechaNacimiento());
        newUsuario.setActivo(true);
        newUsuario.setRol(rol);

        Usuarios modelUsuario = userRepository.save(newUsuario);

        // Crear borrador de emprendimiento asociado al usuario
        Emprendimientos modelEmprendimiento =
                emprendimientoService.crearBorradorEmprendimiento(usuario.getEmprendimiento(), modelUsuario);

        // Crear información del representante
        InformacionRepresentante info = getInformacionRepresentante(usuario, modelEmprendimiento);
        informacionRepresentanteRepository.save(info);

        return modelUsuario;
    }

    private static InformacionRepresentante getInformacionRepresentante(UsuarioEmprendeDTO usuario, Emprendimientos modelEmprendimiento) {
        InformacionRepresentante info = new InformacionRepresentante();
        info.setNombre(usuario.getNombre());
        info.setApellido(usuario.getApellido());
        info.setCorreoPersonal(usuario.getCorreo());
        info.setCorreoCorporativo(usuario.getCorreo());
        info.setIdentificacion(usuario.getIdentificacion());
        info.setCarrera(usuario.getCarrera());
        info.setSemestre(usuario.getSemestre());
        info.setFechaGraduacion(usuario.getFechaGraduacion());
        info.setTieneParientesUees(usuario.getParienteDirecto());
        info.setNombrePariente(usuario.getNombrePariente());
        info.setAreaPariente(usuario.getAreaPariente());
        info.setEmprendimiento(modelEmprendimiento);
        return info;
    }
}
