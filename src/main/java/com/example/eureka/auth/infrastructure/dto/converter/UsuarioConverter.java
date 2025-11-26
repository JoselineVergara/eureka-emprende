package com.example.eureka.auth.infrastructure.dto.converter;

import com.example.eureka.auth.infrastructure.dto.UsuarioPerfilDTO;
import com.example.eureka.auth.domain.Usuarios;
import org.springframework.stereotype.Component;

@Component
public class UsuarioConverter {

    /**
     * Convierte una entidad Usuarios a UsuarioPerfilDTO
     */
    public UsuarioPerfilDTO toPerfilDTO(Usuarios usuario) {
        if (usuario == null) {
            return null;
        }

        UsuarioPerfilDTO dto = new UsuarioPerfilDTO();
        dto.setId(usuario.getId());
        dto.setNombre(usuario.getNombre());
        dto.setApellido(usuario.getApellido());
        dto.setGenero(usuario.getGenero());
        dto.setCorreo(usuario.getCorreo());
        dto.setFechaRegistro(usuario.getFechaRegistro());
        dto.setFechaNacimiento(usuario.getFechaNacimiento());
        dto.setActivo(usuario.getActivo());

        // Mapear rol de forma segura
        if (usuario.getRol() != null) {
            dto.setNombreRol(usuario.getRol().getNombre());
            dto.setIdRol(usuario.getRol().getId());
        }

        return dto;
    }

    /**
     * Convierte un UsuarioPerfilDTO a entidad Usuarios (para updates)
     * NO incluye contraseña ni rol por seguridad
     */
    public Usuarios toEntity(UsuarioPerfilDTO dto) {
        if (dto == null) {
            return null;
        }

        Usuarios usuario = new Usuarios();
        usuario.setId(dto.getId());
        usuario.setNombre(dto.getNombre());
        usuario.setApellido(dto.getApellido());
        usuario.setGenero(dto.getGenero());
        usuario.setCorreo(dto.getCorreo());
        usuario.setFechaRegistro(dto.getFechaRegistro());
        usuario.setFechaNacimiento(dto.getFechaNacimiento());
        usuario.setActivo(dto.getActivo());

        return usuario;
    }
}