package com.example.eureka.entrepreneurship.service;

import com.example.eureka.entrepreneurship.dto.*;
import com.example.eureka.model.Emprendimientos;
import com.example.eureka.model.SolicitudAprobacion;
import com.example.eureka.model.Usuarios;
import jakarta.validation.Valid;

import java.util.List;

public interface IEmprendimientoService {

    Integer estructuraEmprendimiento(EmprendimientoRequestDTO emprendimientoRequestDTO) throws Exception;

    List<EmprendimientoResponseDTO> obtenerEmprendimientos();

    EmprendimientoPorCategoriaDTO obtenerEmprendimientosPorCategoria(Integer categoriaId);

    EmprendimientoResponseDTO obtenerEmprendimientoPorId(Integer id);

    Emprendimientos crearBorradorEmprendimiento(@Valid EmprendimientoDTO emprendimientoDTO, Usuarios usuario);

    EmprendimientoResponseDTO obtenerEmprendimientoCompletoPorId(Integer id);

    EmprendimientoResponseDTO actualizarEmprendimiento(Integer id, EmprendimientoRequestDTO emprendimientoRequestDTO) throws Exception;

    // Nuevos métodos para sistema de aprobación
    SolicitudAprobacion enviarParaAprobacion(Integer emprendimientoId, Usuarios usuario);
    VistaEmprendedorDTO obtenerVistaEmprendedor(Integer emprendimientoId);

    List<EmprendimientoResponseDTO> obtenerEmprendimientosPorUsuario(Usuarios usuario);

    /**
     * Obtener emprendimientos filtrados por nombre, tipo, categoría y ciudad
     * @param nombre
     * @param tipo
     * @param categoria
     * @param ciudad
     * @return
     */
    List<EmprendimientoResponseDTO> obtenerEmprendimientosFiltrado(String nombre, String tipo, String categoria, String ciudad);
}
