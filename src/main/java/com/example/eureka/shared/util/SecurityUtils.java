package com.example.eureka.shared.util;

import com.example.eureka.auth.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
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

    /**
     * Verifica si el usuario autenticado es ADMINISTRADOR
     */
    public boolean esAdministrador() {
        return SecurityContextHolder.getContext()
                .getAuthentication()
                .getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(rol -> rol.equals("ROLE_ADMINISTRADOR") || rol.equals("ADMINISTRADOR"));
    }

    /**
     * Verifica si el usuario autenticado tiene un rol específico
     */
    public boolean tieneRol(String rol) {
        return SecurityContextHolder.getContext()
                .getAuthentication()
                .getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(r -> r.equals(rol) || r.equals("ROLE_" + rol));
    }
}