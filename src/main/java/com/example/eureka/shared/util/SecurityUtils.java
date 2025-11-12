package com.example.eureka.shared.util;

import com.example.eureka.auth.port.out.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SecurityUtils {

    private final IUserRepository userRepository;

    /**
     * Obtiene el ID del usuario autenticado desde el token JWT
     */
    public Integer getIdUsuarioAutenticado() {
        String correoUsuario = getCorreoUsuarioAutenticado();
        return userRepository.obtenerIdPorCorreo(correoUsuario);
    }

    /**
     * Obtiene el correo del usuario autenticado desde el token JWT
     */
    public String getCorreoUsuarioAutenticado() {
        return SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();
    }
}