package com.example.eureka.entrepreneurship.service.impl;

import com.example.eureka.auth.repository.IUserRepository;
import com.example.eureka.entrepreneurship.dto.*;
import com.example.eureka.entrepreneurship.mappers.EmprendimientoMapper;
import com.example.eureka.entrepreneurship.repository.*;
import com.example.eureka.entrepreneurship.service.IEmprendimientoService;
import com.example.eureka.enums.EstadoEmprendimiento;
import com.example.eureka.general.repository.ICiudadesRepository;
import com.example.eureka.general.repository.IDeclaracionesFinalesRepository;
import com.example.eureka.general.repository.IOpcionesParticipacionComunidadRepository;
import com.example.eureka.general.repository.ITiposMetricasRepository;
import com.example.eureka.model.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
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
    private final ITiposEmprendimientoRepository tiposEmprendimientoRepository;

    @Override
    @Transactional
    public void estructuraEmprendimiento(@Valid EmprendimientoRequestDTO emprendimientoRequestDTO) {
        log.info("Iniciando creación de estructura de emprendimiento para usuario: {}",
                emprendimientoRequestDTO.getUsuarioId());

        // Validar request
        if (emprendimientoRequestDTO == null) {
            throw new IllegalArgumentException("Request no puede ser nulo");
        }
        if (emprendimientoRequestDTO.getUsuarioId() == null) {
            throw new IllegalArgumentException("Usuario ID no puede ser nulo");
        }
        if (emprendimientoRequestDTO.getEmprendimiento() == null) {
            throw new IllegalArgumentException("Datos del emprendimiento no pueden ser nulos");
        }

        // Buscar usuario
        Usuarios usuario = userRepository.findById(emprendimientoRequestDTO.getUsuarioId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Usuario no encontrado con ID: " + emprendimientoRequestDTO.getUsuarioId()));

        // Crear emprendimiento principal
        Emprendimientos emprendimiento = crearEmprendimiento(emprendimientoRequestDTO.getEmprendimiento(), usuario);

        // Agregar todas las relaciones (si alguna falla, @Transactional hace rollback de TODO)
        agregarCategoriaEmprendimiento(emprendimiento.getId(), emprendimientoRequestDTO.getCategorias());
        agregarDescripcionEmprendimiento(emprendimiento, emprendimientoRequestDTO.getDescripciones());
        agregarMetricasEmprendimiento(emprendimiento, emprendimientoRequestDTO.getMetricas());
        agregarPresenciaDigitalEmprendimiento(emprendimiento, emprendimientoRequestDTO.getPresenciasDigitales());
        agregarParticipacionComunidad(emprendimiento, emprendimientoRequestDTO.getParticipacionesComunidad());
        agregarDeclaracionesFinales(emprendimiento, emprendimientoRequestDTO.getDeclaracionesFinales());

        log.info("Emprendimiento creado exitosamente con ID: {}", emprendimiento.getId());
    }

    private Emprendimientos crearEmprendimiento(EmprendimientoDTO emprendimientoDTO, Usuarios usuario) {
        log.debug("Creando emprendimiento con nombre: {}", emprendimientoDTO.getNombreComercialEmprendimiento());

        // Buscar ciudad
        Ciudades ciudad = ciudadesRepository.findById(emprendimientoDTO.getCiudad())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Ciudad no encontrada con ID: " + emprendimientoDTO.getCiudad()));

        // Buscar tipo de emprendimiento - CORREGIDO
        TiposEmprendimientos tipoEmprendimiento = tiposEmprendimientoRepository
                .findById(emprendimientoDTO.getTipoEmprendimientoId()) // CORREGIDO: usar getTipoEmprendimientoId()
                .orElseThrow(() -> new EntityNotFoundException(
                        "Tipo de emprendimiento no encontrado con ID: " + emprendimientoDTO.getTipoEmprendimientoId()));

        // Crear entidad emprendimiento - CORREGIDO
        Emprendimientos emprendimiento = new Emprendimientos();
        emprendimiento.setNombreComercial(emprendimientoDTO.getNombreComercialEmprendimiento());
        emprendimiento.setAnioCreacion(emprendimientoDTO.getFechaCreacion());
        emprendimiento.setActivoEmprendimiento(emprendimientoDTO.getEstadoEmpredimiento());
        emprendimiento.setAceptaDatosPublicos(emprendimientoDTO.getDatosPublicos());
        emprendimiento.setFechaCreacion(emprendimientoDTO.getFechaCreacion());
        emprendimiento.setEstadoEmprendimiento(String.valueOf(EstadoEmprendimiento.BORRADOR));
        emprendimiento.setCiudades(ciudad);
        emprendimiento.setUsuarios(usuario);
        emprendimiento.setTiposEmprendimientos(tipoEmprendimiento);

        return emprendimientosRepository.save(emprendimiento);
    }

    private void agregarCategoriaEmprendimiento(Integer codigoEmprendimiento, List<EmprendimientoCategoriaDTO> lsCategorias) {
        if (CollectionUtils.isEmpty(lsCategorias)) {
            log.debug("No hay categorías para agregar");
            return;
        }

        log.debug("Agregando {} categorías al emprendimiento: {}", lsCategorias.size(), codigoEmprendimiento);

        List<EmprendimientoCategorias> categorias = lsCategorias.stream()
                .map(categoriaDTO -> {
                    EmprendimientoCategorias categoria = new EmprendimientoCategorias();
                    categoria.setEmprendimientoId(codigoEmprendimiento);
                    categoria.setCategoriaId(categoriaDTO.getCategoriaId());
                    return categoria;
                })
                .collect(Collectors.toList());

        emprendimientoCategoriasRepository.saveAll(categorias);
    }

    private void agregarDescripcionEmprendimiento(Emprendimientos emprendimiento, List<EmprendimientoDescripcionDTO> lsDescripcion) {
        if (CollectionUtils.isEmpty(lsDescripcion)) {
            log.debug("No hay descripciones para agregar");
            return;
        }

        log.debug("Agregando {} descripciones al emprendimiento: {}", lsDescripcion.size(), emprendimiento.getId());

        List<TiposDescripcionEmprendimiento> descripciones = lsDescripcion.stream()
                .map(dto -> {
                    TiposDescripcionEmprendimiento descripcion = new TiposDescripcionEmprendimiento();
                    descripcion.setTipoDescripcion(dto.getTipoDescripcion());
                    descripcion.setDescripcion(dto.getDescripcion());
                    descripcion.setMaxCaracteres(dto.getMaxCaracteres());
                    descripcion.setObligatorio(dto.getObligatorio());
                    descripcion.setEmprendimiento(emprendimiento);
                    return descripcion;
                })
                .collect(Collectors.toList());

        emprendimientosDescripcionRepository.saveAll(descripciones);
    }

    private void agregarPresenciaDigitalEmprendimiento(Emprendimientos emprendimiento, List<EmprendimientoPresenciaDigitalDTO> lsPresenciaDigital) {
        if (CollectionUtils.isEmpty(lsPresenciaDigital)) {
            log.debug("No hay presencia digital para agregar");
            return;
        }

        log.debug("Agregando {} presencias digitales al emprendimiento: {}", lsPresenciaDigital.size(), emprendimiento.getId());

        List<TiposPresenciaDigital> presencias = lsPresenciaDigital.stream()
                .map(dto -> {
                    TiposPresenciaDigital presencia = new TiposPresenciaDigital();
                    presencia.setDescripcion(dto.getDescripcion());
                    presencia.setPlataforma(dto.getPlataforma());
                    presencia.setEmprendimiento(emprendimiento);
                    return presencia;
                })
                .collect(Collectors.toList());

        emprendimientoPresenciaDigitalRepository.saveAll(presencias);
    }

    private void agregarMetricasEmprendimiento(Emprendimientos emprendimiento, List<EmprendimientoMetricasDTO> lsMetricas) {
        if (CollectionUtils.isEmpty(lsMetricas)) {
            log.debug("No hay métricas para agregar");
            return;
        }

        log.debug("Agregando {} métricas al emprendimiento: {}", lsMetricas.size(), emprendimiento.getId());

        List<EmprendimientoMetricas> metricas = lsMetricas.stream()
                .map(dto -> {
                    MetricasBasicas metrica = tiposMetricasRepository.findById(dto.getMetricaId())
                            .orElseThrow(() -> new EntityNotFoundException(
                                    "Métrica no encontrada con ID: " + dto.getMetricaId()));

                    EmprendimientoMetricas metricaEmp = new EmprendimientoMetricas();
                    metricaEmp.setMetrica(metrica);
                    metricaEmp.setEmprendimiento(emprendimiento);
                    metricaEmp.setValor(dto.getValor());
                    return metricaEmp;
                })
                .collect(Collectors.toList());

        emprendimientoMetricaRepository.saveAll(metricas);
    }

    private void agregarParticipacionComunidad(Emprendimientos emprendimiento, List<EmprendimientoParticipacionDTO> lsParticipacionComunidad) {
        if (CollectionUtils.isEmpty(lsParticipacionComunidad)) {
            log.debug("No hay participaciones en comunidad para agregar");
            return;
        }

        log.debug("Agregando {} participaciones en comunidad al emprendimiento: {}",
                lsParticipacionComunidad.size(), emprendimiento.getId());

        List<EmprendimientoParticipacion> participaciones = lsParticipacionComunidad.stream()
                .map(dto -> {
                    OpcionesParticipacionComunidad opcion = opcionesParticipacionComunidadRepository
                            .findById(dto.getOpcionParticipacionId())
                            .orElseThrow(() -> new EntityNotFoundException(
                                    "Opción de participación no encontrada con ID: " + dto.getOpcionParticipacionId()));

                    EmprendimientoParticipacion participacion = new EmprendimientoParticipacion();
                    participacion.setOpcionParticipacion(opcion);
                    participacion.setEmprendimiento(emprendimiento);
                    participacion.setRespuesta(dto.getRespuesta());
                    return participacion;
                })
                .collect(Collectors.toList());

        emprendimientoParticicipacionComunidadRepository.saveAll(participaciones);
    }

    private void agregarDeclaracionesFinales(Emprendimientos emprendimiento, List<EmprendimientoDeclaracionesDTO> lsDeclaracionesFinales) {
        if (CollectionUtils.isEmpty(lsDeclaracionesFinales)) {
            log.debug("No hay declaraciones finales para agregar");
            return;
        }

        log.debug("Agregando {} declaraciones finales al emprendimiento: {}",
                lsDeclaracionesFinales.size(), emprendimiento.getId());

        List<EmprendimientoDeclaraciones> declaraciones = lsDeclaracionesFinales.stream()
                .map(dto -> {
                    DeclaracionesFinales declaracion = declaracionesFinalesRepository
                            .findById(dto.getDeclaracionId())
                            .orElseThrow(() -> new EntityNotFoundException(
                                    "Declaración final no encontrada con ID: " + dto.getDeclaracionId()));

                    EmprendimientoDeclaraciones declaracionEmp = new EmprendimientoDeclaraciones();
                    declaracionEmp.setAceptada(dto.getAceptada());
                    declaracionEmp.setNombreFirma(dto.getNombreFirma());
                    declaracionEmp.setFechaAceptacion(dto.getFechaAceptacion());
                    declaracionEmp.setEmprendimiento(emprendimiento);
                    declaracionEmp.setDeclaracion(declaracion);
                    return declaracionEmp;
                })
                .collect(Collectors.toList());

        emprendimientoDeclaracionesRepository.saveAll(declaraciones);
    }

    @Override
    public List<EmprendimientoResponseDTO> obtenerEmprendimientos() {
        List<Emprendimientos> lista = emprendimientosRepository.findAll();
        return EmprendimientoMapper.toResponseList(lista);
    }

    @Override
    public EmprendimientoResponseDTO obtenerEmprendimientoPorId(Integer id) {
        Emprendimientos emp = emprendimientosRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Emprendimiento no encontrado con id: " + id));
        return EmprendimientoMapper.toResponseDTO(emp);
    }

    @Transactional
    public Emprendimientos crearBorradorEmprendimiento(@Valid EmprendimientoDTO emprendimientoDTO, Usuarios usuario) {
        log.info("Creando borrador de emprendimiento para usuario: {}", usuario.getId());

        // Buscar ciudad
        Ciudades ciudad = ciudadesRepository.findById(emprendimientoDTO.getCiudad())
                .orElseThrow(() -> new EntityNotFoundException("Ciudad no encontrada con ID: " + emprendimientoDTO.getCiudad()));

        // Buscar tipo de emprendimiento - CORREGIDO
        TiposEmprendimientos tipoEmprendimiento = tiposEmprendimientoRepository
                .findById(emprendimientoDTO.getTipoEmprendimientoId()) // CORREGIDO: usar getTipoEmprendimientoId()
                .orElseThrow(() -> new EntityNotFoundException(
                        "Tipo de emprendimiento no encontrado con ID: " + emprendimientoDTO.getTipoEmprendimientoId()));

        // Crear entidad emprendimiento - CORREGIDO
        Emprendimientos emprendimiento = new Emprendimientos();
        emprendimiento.setNombreComercial(emprendimientoDTO.getNombreComercialEmprendimiento());
        emprendimiento.setAnioCreacion(emprendimientoDTO.getFechaCreacion());
        emprendimiento.setActivoEmprendimiento(emprendimientoDTO.getEstadoEmpredimiento());
        emprendimiento.setAceptaDatosPublicos(emprendimientoDTO.getDatosPublicos());
        emprendimiento.setFechaCreacion(emprendimientoDTO.getFechaCreacion());
        emprendimiento.setEstadoEmprendimiento(String.valueOf(EstadoEmprendimiento.BORRADOR));
        emprendimiento.setUsuarios(usuario);
        emprendimiento.setCiudades(ciudad);
        emprendimiento.setTiposEmprendimientos(tipoEmprendimiento);

        Emprendimientos guardado = emprendimientosRepository.save(emprendimiento);
        log.info("Borrador creado exitosamente con ID: {}", guardado.getId());

        return guardado;
    }
}