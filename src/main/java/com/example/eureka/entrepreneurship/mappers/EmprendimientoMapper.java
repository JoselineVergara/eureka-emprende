package com.example.eureka.entrepreneurship.mappers;

import com.example.eureka.entrepreneurship.dto.*;
import com.example.eureka.general.dto.CategoriasDTO;
import com.example.eureka.model.*;

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

    public static List<EmprendimientoCategoriaDTO> toCategoriaDTOList(List<EmprendimientoCategorias> categorias) {
        return categorias.stream().map(cat -> {
            EmprendimientoCategoriaDTO dto = new EmprendimientoCategoriaDTO();

            // Mapear emprendimiento (solo info básica)
            if (cat.getEmprendimiento() != null) {
                EmprendimientoDTO empDTO = new EmprendimientoDTO();
                empDTO.setId(cat.getEmprendimiento().getId());
                empDTO.setNombreComercialEmprendimiento(cat.getEmprendimiento().getNombreComercial());
                dto.setEmprendimiento(empDTO);
            }

            // Mapear categoría completa
            if (cat.getCategoria() != null) {
                CategoriasDTO catDTO = new CategoriasDTO();
                catDTO.setId(cat.getCategoria().getId());
                catDTO.setNombre(cat.getCategoria().getNombre());
                catDTO.setDescripcion(cat.getCategoria().getDescripcion());
                catDTO.setUrlImagen(cat.getCategoria().getMultimedia().getUrlArchivo());
                catDTO.setIdMultimedia(cat.getCategoria().getMultimedia().getId());

                dto.setCategoria(catDTO);
                dto.setNombreCategoria(cat.getCategoria().getNombre());
            }

            return dto;
        }).collect(Collectors.toList());
    }

    public static List<EmprendimientoDescripcionDTO> toDescripcionDTOList(List<TiposDescripcionEmprendimiento> descripciones) {
        return descripciones.stream().map(desc -> {
            EmprendimientoDescripcionDTO dto = new EmprendimientoDescripcionDTO();
            dto.setTipoDescripcion(desc.getTipoDescripcion());
            dto.setDescripcion(desc.getDescripcion());
            dto.setMaxCaracteres(desc.getMaxCaracteres());
            dto.setObligatorio(desc.getObligatorio());
            dto.setEmprendimientoId(desc.getEmprendimiento().getId());
            return dto;
        }).collect(Collectors.toList());
    }

    public static List<EmprendimientoPresenciaDigitalDTO> toPresenciaDigitalDTOList(List<TiposPresenciaDigital> presencias) {
        return presencias.stream().map(p -> {
            EmprendimientoPresenciaDigitalDTO dto = new EmprendimientoPresenciaDigitalDTO();
            dto.setPlataforma(p.getPlataforma());
            dto.setDescripcion(p.getDescripcion());
            return dto;
        }).collect(Collectors.toList());
    }

    public static List<EmprendimientoMetricasDTO> toMetricasDTOList(List<EmprendimientoMetricas> metricas) {
        return metricas.stream().map(m -> {
            EmprendimientoMetricasDTO dto = new EmprendimientoMetricasDTO();
            dto.setMetricaId(m.getMetrica().getId());
            dto.setValor(m.getValor());
            dto.setEmprendimientoId(m.getEmprendimiento().getId());
            return dto;
        }).collect(Collectors.toList());
    }

    public static List<EmprendimientoDeclaracionesDTO> toDeclaracionesDTOList(List<EmprendimientoDeclaraciones> declaraciones) {
        return declaraciones.stream().map(d -> {
            EmprendimientoDeclaracionesDTO dto = new EmprendimientoDeclaracionesDTO();
            dto.setDeclaracionId(d.getDeclaracion().getId());
            dto.setAceptada(d.getAceptada());
            dto.setNombreFirma(d.getNombreFirma());
            dto.setFechaAceptacion(d.getFechaAceptacion());
            dto.setEmprendimientoId(d.getEmprendimiento().getId());
            return dto;
        }).collect(Collectors.toList());
    }

    public static List<EmprendimientoParticipacionDTO> toParticipacionDTOList(List<EmprendimientoParticipacion> participaciones) {
        return participaciones.stream().map(p -> {
            EmprendimientoParticipacionDTO dto = new EmprendimientoParticipacionDTO();
            dto.setOpcionParticipacionId(p.getOpcionParticipacion().getId());
            dto.setRespuesta(p.getRespuesta());
            dto.setEmprendimientoId(p.getEmprendimiento().getId());
            if (p.getOpcionParticipacion() != null) {
                dto.setEmprendimientoId(p.getEmprendimiento().getId());
                dto.setNombreOpcionParticipacion(p.getOpcionParticipacion().getOpcion());
            }
            return dto;
        }).collect(Collectors.toList());
    }

    public static InformacionRepresentanteDTO toRepresentanteDTO(InformacionRepresentante info) {
        if (info == null) return null;
        InformacionRepresentanteDTO dto = new InformacionRepresentanteDTO();
        dto.setNombre(info.getNombre());
        dto.setApellido(info.getApellido());
        dto.setCorreoPersonal(info.getCorreoPersonal());
        dto.setCorreoCorporativo(info.getCorreoCorporativo());
        dto.setIdentificacion(info.getIdentificacion());
        dto.setCarrera(info.getCarrera());
        dto.setAreaPariente(info.getAreaPariente());
        dto.setSemestre(info.getSemestre());
        dto.setFechaGraduacion(info.getFechaGraduacion());
        dto.setTieneParientesUees(info.getTieneParientesUees());
        dto.setNombrePariente(info.getNombrePariente());

        return dto;
    }

    public static CategoriaDTO toCategoriaDTO(Categorias categoria) {
        if (categoria == null) return null;
        CategoriaDTO dto = new CategoriaDTO();
        dto.setId(categoria.getId());
        dto.setNombre(categoria.getNombre());
        dto.setDescripcion(categoria.getDescripcion());
        if (categoria.getMultimedia() != null) {
            dto.setMultimediaId(categoria.getMultimedia().getId());
        }
        return dto;
    }
}
