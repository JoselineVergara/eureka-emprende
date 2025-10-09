package com.example.eureka.entrepreneurship.service.impl;

import com.example.eureka.auth.repository.IUserRepository;
import com.example.eureka.entrepreneurship.dto.*;
import com.example.eureka.entrepreneurship.mappers.EmprendimientoMapper;
import com.example.eureka.entrepreneurship.repository.*;
import com.example.eureka.entrepreneurship.service.IEmprendimientoService;
import com.example.eureka.general.repository.ICiudadesRepository;
import com.example.eureka.general.repository.IDeclaracionesFinalesRepository;
import com.example.eureka.general.repository.IOpcionesParticipacionComunidadRepository;
import com.example.eureka.general.repository.ITiposMetricasRepository;
import com.example.eureka.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmprendimientoServiceImpl implements IEmprendimientoService {

    private final IUserRepository userRepository;
    private final ICiudadesRepository ciudadesRepository;
    private final IEmprendimientosRepository emprendimientosRepository;
    private final IEmprendimientoCategoriasRepository emprendimientoCategoriasRepository;
    private final IEmprendimientosDescripcionRepository emprendimientosDescripcionRepository;
    private final IEmprendimientoPresenciaDigitalRepository emprendimientoPresenciaDigitalRepository;
    private final IEmprendimientoMetricaRepository emprendimientoMetricaRepository;
    private final IEmprendimientoDeclaracionesRepository emprendimientoDeclaracionesRepository;
    private final IEmprendimientoParticicipacionComunidadRepository emprendimientoParticicipacionComunidadRepository;
    private final ITiposMetricasRepository tiposMetricasRepository;
    private final IOpcionesParticipacionComunidadRepository opcionesParticipacionComunidadRepository;
    private final IDeclaracionesFinalesRepository declaracionesFinalesRepository;

    // Falta multimedia (pendiente)

    @Override
    public void estructuraEmprendimiento(EmprendimientoRequestDTO emprendimientoRequestDTO) throws Exception {

        Usuarios usuario = userRepository.findById(emprendimientoRequestDTO.getUsuarioId())
                .orElseThrow(() -> new Exception("Usuario no encontrado"));

        Emprendimientos emprendimiento = crearEmprendimiento(emprendimientoRequestDTO.getEmprendimiento());

        if (emprendimiento == null) {
            throw new Exception("No se pudo crear el emprendimiento");
        }

        agregarCategoriaEmprendimiento(emprendimiento.getId(), emprendimientoRequestDTO.getCategorias());
        agregarDescripcionEmprendimiento(emprendimiento, emprendimientoRequestDTO.getDescripciones());
        agregarMetricasEmprendimiento(emprendimiento, emprendimientoRequestDTO.getMetricas());
        agregarPresenciaDigitalEmprendimiento(emprendimiento, emprendimientoRequestDTO.getPresenciasDigitales());
        agregarParticipacionComunidad(emprendimiento, emprendimientoRequestDTO.getParticipacionesComunidad());
        agregarDeclaracionesFinales(emprendimiento, emprendimientoRequestDTO.getDeclaracionesFinales());
    }

    private Emprendimientos crearEmprendimiento(EmprendimientoDTO emprendimientoDTO) throws Exception {
        if (emprendimientoDTO == null) {
            throw new Exception("Datos del emprendimiento vacíos");
        }

        Ciudades ciudad = ciudadesRepository.findById(emprendimientoDTO.getCiudad())
                .orElseThrow(() -> new Exception("Ciudad no encontrada"));

        Emprendimientos emprendimiento = new Emprendimientos();
        emprendimiento.setNombreComercial(emprendimientoDTO.getNombreComercialEmprendimiento());
        emprendimiento.setAnioCreacion(emprendimientoDTO.getFechaCreacion());
        emprendimiento.setActivoEmprendimiento(emprendimientoDTO.getEstadoEmpredimiento());
        emprendimiento.setAceptaDatosPublicos(emprendimientoDTO.getDatosPublicos());
        emprendimiento.setFechaCreacion(emprendimientoDTO.getFechaCreacion());
        emprendimiento.setEstadoEmprendimiento(emprendimientoDTO.getTipoEmprendimiento());
        emprendimiento.setCiudades(ciudad);

        return emprendimientosRepository.save(emprendimiento);
    }

    private Boolean agregarCategoriaEmprendimiento(Integer codigoEmprendimiento, List<EmprendimientoCategoriaDTO> lsCategorias) {
        if (lsCategorias == null || lsCategorias.isEmpty()) return false;

        for (EmprendimientoCategoriaDTO categoriaDTO : lsCategorias) {
            EmprendimientoCategorias categoria = new EmprendimientoCategorias();
            categoria.setEmprendimientoId(codigoEmprendimiento);
            categoria.setCategoriaId(categoriaDTO.getCategoriaId());
            emprendimientoCategoriasRepository.save(categoria);
        }
        return true;
    }

    private Boolean agregarDescripcionEmprendimiento(Emprendimientos emprendimiento, List<EmprendimientoDescripcionDTO> lsDescripcion) {
        if (lsDescripcion == null || lsDescripcion.isEmpty()) return false;

        for (EmprendimientoDescripcionDTO dto : lsDescripcion) {
            TiposDescripcionEmprendimiento descripcion = new TiposDescripcionEmprendimiento();
            descripcion.setTipoDescripcion(dto.getTipoDescripcion());
            descripcion.setDescripcion(dto.getDescripcion());
            descripcion.setMaxCaracteres(dto.getMaxCaracteres());
            descripcion.setObligatorio(dto.getObligatorio());
            descripcion.setEmprendimiento(emprendimiento);
            emprendimientosDescripcionRepository.save(descripcion);
        }
        return true;
    }

    private Boolean agregarPresenciaDigitalEmprendimiento(Emprendimientos emprendimiento, List<EmprendimientoPresenciaDigitalDTO> lsPresenciaDigital) {
        if (lsPresenciaDigital == null || lsPresenciaDigital.isEmpty()) return false;

        for (EmprendimientoPresenciaDigitalDTO dto : lsPresenciaDigital) {
            TiposPresenciaDigital presencia = new TiposPresenciaDigital();
            presencia.setDescripcion(dto.getDescripcion());
            presencia.setPlataforma(dto.getPlataforma());
            presencia.setEmprendimiento(emprendimiento);
            emprendimientoPresenciaDigitalRepository.save(presencia);
        }
        return true;
    }

    private Boolean agregarMetricasEmprendimiento(Emprendimientos emprendimiento, List<EmprendimientoMetricasDTO> lsMetricas) throws Exception {
        if (lsMetricas == null || lsMetricas.isEmpty()) return false;

        for (EmprendimientoMetricasDTO dto : lsMetricas) {
            MetricasBasicas metrica = tiposMetricasRepository.findById(dto.getMetricaId())
                    .orElseThrow(() -> new Exception("Métrica no encontrada"));

            EmprendimientoMetricas metricaEmp = new EmprendimientoMetricas();
            metricaEmp.setMetrica(metrica);
            metricaEmp.setEmprendimiento(emprendimiento);
            metricaEmp.setValor(dto.getValor());
            emprendimientoMetricaRepository.save(metricaEmp);
        }
        return true;
    }

    private Boolean agregarParticipacionComunidad(Emprendimientos emprendimiento, List<EmprendimientoParticipacionDTO> lsParticipacionComunidad) throws Exception {
        if (lsParticipacionComunidad == null || lsParticipacionComunidad.isEmpty()) return false;

        for (EmprendimientoParticipacionDTO dto : lsParticipacionComunidad) {
            OpcionesParticipacionComunidad opcion = opcionesParticipacionComunidadRepository.findById(dto.getOpcionParticipacionId())
                    .orElseThrow(() -> new Exception("Participación Comunidad no encontrada"));

            EmprendimientoParticipacion participacion = new EmprendimientoParticipacion();
            participacion.setOpcionParticipacion(opcion);
            participacion.setEmprendimiento(emprendimiento);
            participacion.setRespuesta(dto.getRespuesta());
            emprendimientoParticicipacionComunidadRepository.save(participacion);
        }
        return true;
    }

    private Boolean agregarDeclaracionesFinales(Emprendimientos emprendimiento, List<EmprendimientoDeclaracionesDTO> lsDeclaracionesFinales) throws Exception {
        if (lsDeclaracionesFinales == null || lsDeclaracionesFinales.isEmpty()) return false;

        for (EmprendimientoDeclaracionesDTO dto : lsDeclaracionesFinales) {
            DeclaracionesFinales declaracion = declaracionesFinalesRepository.findById(dto.getDeclaracionId())
                    .orElseThrow(() -> new Exception("Declaración final no encontrada"));

            EmprendimientoDeclaraciones declaracionEmp = new EmprendimientoDeclaraciones();
            declaracionEmp.setAceptada(dto.getAceptada());
            declaracionEmp.setNombreFirma(dto.getNombreFirma());
            declaracionEmp.setFechaAceptacion(dto.getFechaAceptacion());
            declaracionEmp.setEmprendimiento(emprendimiento);
            declaracionEmp.setDeclaracion(declaracion);
            emprendimientoDeclaracionesRepository.save(declaracionEmp);
        }
        return true;
    }

    @Override
    public List<EmprendimientoResponseDTO> obtenerEmprendimientos() {
        List<Emprendimientos> lista = emprendimientosRepository.findAll();
        return EmprendimientoMapper.toResponseList(lista);
    }

    @Override
    public EmprendimientoResponseDTO obtenerEmprendimientoPorId(Integer id) {
        Emprendimientos emp = emprendimientosRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Emprendimiento no encontrado con id: " + id));
        return EmprendimientoMapper.toResponseDTO(emp);
    }
}
