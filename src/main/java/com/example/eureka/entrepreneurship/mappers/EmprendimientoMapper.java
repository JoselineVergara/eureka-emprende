package com.example.eureka.entrepreneurship.mappers;

import com.example.eureka.entrepreneurship.dto.EmprendimientoResponseDTO;
import com.example.eureka.model.Emprendimientos;

import java.util.List;
import java.util.stream.Collectors;

public class EmprendimientoMapper {

    public static EmprendimientoResponseDTO toResponseDTO(Emprendimientos emprendimiento) {
        if (emprendimiento == null) return null;

        EmprendimientoResponseDTO dto = new EmprendimientoResponseDTO();

        dto.setId(emprendimiento.getId());
        dto.setNombreComercial(emprendimiento.getNombreComercial());
        dto.setAnioCreacion(emprendimiento.getAnioCreacion());
        dto.setActivoEmprendimiento(emprendimiento.getActivoEmprendimiento());
        dto.setAceptaDatosPublicos(emprendimiento.getAceptaDatosPublicos());
        dto.setFechaCreacion(emprendimiento.getFechaCreacion());
        dto.setFechaActualizacion(emprendimiento.getFechaActualizacion());
        dto.setEstadoEmprendimiento(emprendimiento.getEstadoEmprendimiento());

        // --- Relaciones ---
        if (emprendimiento.getUsuarios() != null) {
            dto.setUsuarioId(emprendimiento.getUsuarios().getId());
            dto.setNombreUsuario(emprendimiento.getUsuarios().getNombre());
        }

        if (emprendimiento.getCiudades() != null) {
            dto.setCiudadId(emprendimiento.getCiudades().getId());
            dto.setNombreCiudad(emprendimiento.getCiudades().getNombreCiudad());
        }

        if (emprendimiento.getTiposEmprendimientos() != null) {
            dto.setTipoEmprendimientoId(emprendimiento.getTiposEmprendimientos().getId());
            dto.setNombreTipoEmprendimiento(emprendimiento.getTiposEmprendimientos().getSubTipo());
        }

        return dto;
    }

    public static List<EmprendimientoResponseDTO> toResponseList(List<Emprendimientos> emprendimientos) {
        return emprendimientos.stream()
                .map(EmprendimientoMapper::toResponseDTO)
                .collect(Collectors.toList());
    }
}
