package com.example.eureka.entrepreneurship.service.impl;

import com.example.eureka.auth.dto.UsuarioEmprendeDTO;
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
import java.util.Collections;
import java.util.List;
import java.util.Map;
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
    private final IRepresentanteInformacionRepository representanteInformacionRepository;
    private final IOpcionesParticipacionComunidadRepository opcionesParticipacionComunidadRepository;
    private final IDeclaracionesFinalesRepository declaracionesFinalesRepository;
    private final ICategoriaRepository categoriaRepository; // NUEVO


    // Falta multimedia (pendiente)
    private final ITiposEmprendimientoRepository tiposEmprendimientoRepository;
    private final IRepresentanteInformacionRepository informacionRepresentanteRepository;

    // Agregar estas dependencias al inicio de la clase
    private final SolicitudAprobacionService solicitudAprobacionService;

    /**
     * Enviar emprendimiento para aprobación (desde BORRADOR o PUBLICADO)
     */
    @Override
    @Transactional
    public SolicitudAprobacion enviarParaAprobacion(Integer emprendimientoId, Usuarios usuario) {
        log.info("Enviando emprendimiento {} para aprobación", emprendimientoId);

        // Capturar estado completo actual
        EmprendimientoCompletoDTO datosCompletos = solicitudAprobacionService
                .capturarEstadoCompleto(emprendimientoId);

        // Crear solicitud
        return solicitudAprobacionService.crearSolicitud(emprendimientoId, datosCompletos, usuario);
    }

    /**
     * Obtener vista para el emprendedor con datos actuales y pendientes
     */
    @Override
    public VistaEmprendedorDTO obtenerVistaEmprendedor(Integer emprendimientoId) {
        return solicitudAprobacionService.obtenerVistaEmprendedor(emprendimientoId);
    }

    @Override
    @Transactional
    public Integer estructuraEmprendimiento(@Valid EmprendimientoRequestDTO emprendimientoRequestDTO) {
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
        Emprendimientos emprendimiento = crearEmprendimiento(emprendimientoRequestDTO.getEmprendimiento(), usuario,emprendimientoRequestDTO.getTipoAccion());

//        InformacionRepresentante info = getInformacionRepresentante(usuario, emprendimiento);
//        if (info != null) {
//            informacionRepresentanteRepository.save(info);
//        }
        // Agregar todas las relaciones (si alguna falla, @Transactional hace rollback de TODO)
        if (emprendimientoRequestDTO.getTipoAccion().equals("CREAR")){
            agregarCategoriaEmprendimiento(emprendimiento, emprendimientoRequestDTO.getCategorias());
            agregarDescripcionEmprendimiento(emprendimiento, emprendimientoRequestDTO.getDescripciones());
            agregarMetricasEmprendimiento(emprendimiento, emprendimientoRequestDTO.getMetricas());
            agregarPresenciaDigitalEmprendimiento(emprendimiento, emprendimientoRequestDTO.getPresenciasDigitales());
            agregarParticipacionComunidad(emprendimiento, emprendimientoRequestDTO.getParticipacionesComunidad());
            agregarDeclaracionesFinales(emprendimiento, emprendimientoRequestDTO.getDeclaracionesFinales());
        }
        log.info("Emprendimiento creado exitosamente with ID: {}", emprendimiento.getId());

        return emprendimiento.getId();
    }

    private Emprendimientos crearEmprendimiento(EmprendimientoDTO emprendimientoDTO, Usuarios usuario,String tipoAccion) {
        log.debug("Creando emprendimiento con nombre: {}", emprendimientoDTO.getNombreComercialEmprendimiento());

        // Buscar ciudad
        Ciudades ciudad = ciudadesRepository.findById(emprendimientoDTO.getCiudad())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Ciudad no encontrada con ID: " + emprendimientoDTO.getCiudad()));

        // Buscar tipo de emprendimiento - CORREGIDO
        TiposEmprendimientos tipoEmprendimiento = tiposEmprendimientoRepository
                .findById(emprendimientoDTO.getTipoEmprendimientoId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Tipo de emprendimiento no encontrado con ID: " + emprendimientoDTO.getTipoEmprendimientoId()));

        // Crear entidad emprendimiento - CORREGIDO
        Emprendimientos emprendimiento = new Emprendimientos();
        emprendimiento.setNombreComercial(emprendimientoDTO.getNombreComercialEmprendimiento());
        emprendimiento.setAnioCreacion(emprendimientoDTO.getFechaCreacion());
        emprendimiento.setActivoEmprendimiento(emprendimientoDTO.getEstadoEmpredimiento());
        emprendimiento.setAceptaDatosPublicos(emprendimientoDTO.getDatosPublicos());
        emprendimiento.setFechaCreacion(emprendimientoDTO.getFechaCreacion());

        if (tipoAccion.equals(String.valueOf(EstadoEmprendimiento.BORRADOR))){
            emprendimiento.setEstadoEmprendimiento(String.valueOf(EstadoEmprendimiento.BORRADOR));
        }else{
            emprendimiento.setEstadoEmprendimiento(String.valueOf(EstadoEmprendimiento.PENDIENTE_APROBACION));
        }

        emprendimiento.setCiudades(ciudad);
        emprendimiento.setUsuarios(usuario);
        emprendimiento.setTiposEmprendimientos(tipoEmprendimiento);

        return emprendimientosRepository.save(emprendimiento);
    }

    private void agregarCategoriaEmprendimiento(Emprendimientos emprendimientos, List<EmprendimientoCategoriaDTO> lsCategorias) {
        if (CollectionUtils.isEmpty(lsCategorias)) {
            log.debug("No hay categorías para agregar");
            return;
        }

        log.debug("Agregando {} categorías al emprendimiento: {}", lsCategorias.size(), emprendimientos);

        List<EmprendimientoCategorias> categorias = lsCategorias.stream()
                .map(categoriaDTO -> {
                    EmprendimientoCategorias categoria = new EmprendimientoCategorias();
                    categoria.setEmprendimiento(emprendimientos);
                    Categorias cat = emprendimientoCategoriasRepository.findById(categoriaDTO.getCategoria().getId())
                            .orElseThrow(() -> new EntityNotFoundException(
                                    "Categoría no encontrada con ID: " + categoriaDTO.getCategoria())).getCategoria();
                    categoria.setCategoria(cat);
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

    @Override
    public EmprendimientoPorCategoriaDTO obtenerEmprendimientosPorCategoria(Integer categoriaId) {
        Categorias categoria = categoriaRepository.findById(categoriaId)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada con id: " + categoriaId));

        List<EmprendimientoCategorias> emprendimientoCategorias =
                emprendimientoCategoriasRepository.findByCategoriaId(categoriaId);

        List<EmprendimientoSimpleDTO> emprendimientos = emprendimientoCategorias.stream()
                .map(ec -> {
                    Emprendimientos emp = ec.getEmprendimiento();
                    return EmprendimientoSimpleDTO.builder()
                            .id(emp.getId().longValue())
                            .nombreComercial(emp.getNombreComercial())
                            //.ciudad(emp.getCiudades().getNombre())
                            .build();
                })
                .collect(Collectors.toList());

        return EmprendimientoPorCategoriaDTO.builder()
                .categoriaId(categoria.getId())
                .nombreCategoria(categoria.getNombre())
                .emprendimientos(emprendimientos)
                .build();
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

    public InformacionRepresentante getInformacionRepresentante(Usuarios usuario, Emprendimientos modelEmprendimiento) {
        InformacionRepresentante info = new InformacionRepresentante();
        info.setNombre(usuario.getNombre());
        info.setApellido(usuario.getApellido());
        info.setCorreoPersonal(usuario.getCorreo());
        info.setCorreoCorporativo(usuario.getCorreo());
//        info.setIdentificacion(usuario.getIdentificacion());
//        info.setCarrera(usuario.getCarrera());
//        info.setSemestre(usuario.getSemestre());
//        info.setFechaGraduacion(usuario.getFechaGraduacion());
//        info.setTieneParientesUees(usuario.getParienteDirecto());
//        info.setNombrePariente(usuario.getNombrePariente());
//        info.setAreaPariente(usuario.getAreaPariente());
        info.setEmprendimiento(modelEmprendimiento);
        return info;
    }

    @Override
    @Transactional
    public EmprendimientoResponseDTO obtenerEmprendimientoCompletoPorId(Integer id) {
        Emprendimientos emprendimiento = emprendimientosRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Emprendimiento no encontrado con id: " + id));

        EmprendimientoResponseDTO dto = EmprendimientoMapper.toResponseDTO(emprendimiento);
        dto.setCategorias(EmprendimientoMapper.toCategoriaDTOList(
            emprendimientoCategoriasRepository.findEmprendimientosPorCategoria(id)
        ));
        dto.setDescripciones(EmprendimientoMapper.toDescripcionDTOList(emprendimientosDescripcionRepository.findByEmprendimientoId(id)));
        dto.setPresenciasDigitales(EmprendimientoMapper.toPresenciaDigitalDTOList(emprendimientoPresenciaDigitalRepository.findByEmprendimientoId(id)));
        dto.setMetricas(EmprendimientoMapper.toMetricasDTOList(emprendimientoMetricaRepository.findByEmprendimientoId(id)));
        dto.setDeclaracionesFinales(EmprendimientoMapper.toDeclaracionesDTOList(emprendimientoDeclaracionesRepository.findByEmprendimientoId(id)));
        dto.setParticipacionesComunidad(EmprendimientoMapper.toParticipacionDTOList(
            emprendimientoParticicipacionComunidadRepository.findByEmprendimientoIdFetchOpcion(id)
        ));
        dto.setInformacionRepresentante(EmprendimientoMapper.toRepresentanteDTO(informacionRepresentanteRepository.findFirstByEmprendimientoId(id)));
        return dto;
    }

    @Override
    @Transactional
    public EmprendimientoResponseDTO actualizarEmprendimiento(Integer id, EmprendimientoRequestDTO emprendimientoRequestDTO) throws Exception {
        Emprendimientos emprendimiento = emprendimientosRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Emprendimiento no encontrado con ID: " + id));

        // Actualizar campos principales
        EmprendimientoDTO dto = emprendimientoRequestDTO.getEmprendimiento();
        if (dto != null) {
            emprendimiento.setNombreComercial(dto.getNombreComercialEmprendimiento());
            emprendimiento.setAnioCreacion(dto.getFechaCreacion());
            emprendimiento.setActivoEmprendimiento(dto.getEstadoEmpredimiento());
            emprendimiento.setAceptaDatosPublicos(dto.getDatosPublicos());
            emprendimiento.setFechaCreacion(dto.getFechaCreacion());
            // Actualizar ciudad y tipo de emprendimiento si corresponde
            if (dto.getCiudad() != null) {
                Ciudades ciudad = ciudadesRepository.findById(dto.getCiudad())
                        .orElseThrow(() -> new EntityNotFoundException("Ciudad no encontrada con ID: " + dto.getCiudad()));
                emprendimiento.setCiudades(ciudad);
            }
            if (dto.getTipoEmprendimientoId() != null) {
                TiposEmprendimientos tipoEmprendimiento = tiposEmprendimientoRepository.findById(dto.getTipoEmprendimientoId())
                        .orElseThrow(() -> new EntityNotFoundException("Tipo de emprendimiento no encontrado con ID: " + dto.getTipoEmprendimientoId()));
                emprendimiento.setTiposEmprendimientos(tipoEmprendimiento);
            }
        }
        emprendimientosRepository.save(emprendimiento);

        // Actualizar relaciones (eliminar y volver a agregar)
        emprendimientoCategoriasRepository.deleteEmprendimientoCategoriasByEmprendimientoId((id));
        agregarCategoriaEmprendimiento(emprendimiento, emprendimientoRequestDTO.getCategorias());

        // Actualizar descripciones
        List<TiposDescripcionEmprendimiento> actualesDescripciones = emprendimientosDescripcionRepository.findByEmprendimientoId(id);
        List<EmprendimientoDescripcionDTO> nuevasDescripciones = emprendimientoRequestDTO.getDescripciones() != null ? emprendimientoRequestDTO.getDescripciones() : List.of();
        for (TiposDescripcionEmprendimiento actual : actualesDescripciones) {
            boolean existe = nuevasDescripciones.stream().anyMatch(d -> d.getTipoDescripcion().equals(actual.getTipoDescripcion()));
            if (!existe) {
                emprendimientosDescripcionRepository.delete(actual);
            }
        }
        for (EmprendimientoDescripcionDTO nueva : nuevasDescripciones) {
            TiposDescripcionEmprendimiento actual = actualesDescripciones.stream().filter(d -> d.getTipoDescripcion().equals(nueva.getTipoDescripcion())).findFirst().orElse(null);
            if (actual != null) {
                actual.setDescripcion(nueva.getDescripcion());
                actual.setMaxCaracteres(nueva.getMaxCaracteres());
                actual.setObligatorio(nueva.getObligatorio());
                emprendimientosDescripcionRepository.save(actual);
            } else {
                TiposDescripcionEmprendimiento nuevaDesc = new TiposDescripcionEmprendimiento();
                nuevaDesc.setTipoDescripcion(nueva.getTipoDescripcion());
                nuevaDesc.setDescripcion(nueva.getDescripcion());
                nuevaDesc.setMaxCaracteres(nueva.getMaxCaracteres());
                nuevaDesc.setObligatorio(nueva.getObligatorio());
                nuevaDesc.setEmprendimiento(emprendimiento);
                emprendimientosDescripcionRepository.save(nuevaDesc);
            }
        }
        // Actualizar métricas
        List<EmprendimientoMetricas> actualesMetricas = emprendimientoMetricaRepository.findByEmprendimientoId(id);
        List<EmprendimientoMetricasDTO> nuevasMetricas = emprendimientoRequestDTO.getMetricas() != null ? emprendimientoRequestDTO.getMetricas() : List.of();
        for (EmprendimientoMetricas actual : actualesMetricas) {
            boolean existe = nuevasMetricas.stream().anyMatch(m -> m.getMetricaId().equals(actual.getMetrica().getId()));
            if (!existe) {
                emprendimientoMetricaRepository.delete(actual);
            }
        }
        for (EmprendimientoMetricasDTO nueva : nuevasMetricas) {
            EmprendimientoMetricas actual = actualesMetricas.stream().filter(m -> m.getMetrica().getId().equals(nueva.getMetricaId())).findFirst().orElse(null);
            if (actual != null) {
                actual.setValor(nueva.getValor());
                emprendimientoMetricaRepository.save(actual);
            } else {
                MetricasBasicas metrica = tiposMetricasRepository.findById(nueva.getMetricaId()).orElseThrow();
                EmprendimientoMetricas nuevaMetrica = new EmprendimientoMetricas();
                nuevaMetrica.setMetrica(metrica);
                nuevaMetrica.setEmprendimiento(emprendimiento);
                nuevaMetrica.setValor(nueva.getValor());
                emprendimientoMetricaRepository.save(nuevaMetrica);
            }
        }
        // Actualizar presencia digital
        List<TiposPresenciaDigital> actualesPresencias = emprendimientoPresenciaDigitalRepository.findByEmprendimientoId(id);
        List<EmprendimientoPresenciaDigitalDTO> nuevasPresencias = emprendimientoRequestDTO.getPresenciasDigitales() != null ? emprendimientoRequestDTO.getPresenciasDigitales() : List.of();
        for (TiposPresenciaDigital actual : actualesPresencias) {
            boolean existe = nuevasPresencias.stream().anyMatch(p -> p.getPlataforma().equals(actual.getPlataforma()));
            if (!existe) {
                emprendimientoPresenciaDigitalRepository.delete(actual);
            }
        }
        for (EmprendimientoPresenciaDigitalDTO nueva : nuevasPresencias) {
            TiposPresenciaDigital actual = actualesPresencias.stream().filter(p -> p.getPlataforma().equals(nueva.getPlataforma())).findFirst().orElse(null);
            if (actual != null) {
                actual.setDescripcion(nueva.getDescripcion());
                emprendimientoPresenciaDigitalRepository.save(actual);
            } else {
                TiposPresenciaDigital nuevaPres = new TiposPresenciaDigital();
                nuevaPres.setPlataforma(nueva.getPlataforma());
                nuevaPres.setDescripcion(nueva.getDescripcion());
                nuevaPres.setEmprendimiento(emprendimiento);
                emprendimientoPresenciaDigitalRepository.save(nuevaPres);
            }
        }
        // Actualizar participación comunidad
        List<EmprendimientoParticipacion> actualesParticipaciones = emprendimientoParticicipacionComunidadRepository.findByEmprendimientoId(id);
        List<EmprendimientoParticipacionDTO> nuevasParticipaciones = emprendimientoRequestDTO.getParticipacionesComunidad() != null ? emprendimientoRequestDTO.getParticipacionesComunidad() : List.of();
        for (EmprendimientoParticipacion actual : actualesParticipaciones) {
            boolean existe = nuevasParticipaciones.stream().anyMatch(p -> p.getOpcionParticipacionId().equals(actual.getOpcionParticipacion().getId()));
            if (!existe) {
                emprendimientoParticicipacionComunidadRepository.delete(actual);
            }
        }
        for (EmprendimientoParticipacionDTO nueva : nuevasParticipaciones) {
            EmprendimientoParticipacion actual = actualesParticipaciones.stream().filter(p -> p.getOpcionParticipacion().getId().equals(nueva.getOpcionParticipacionId())).findFirst().orElse(null);
            if (actual != null) {
                actual.setRespuesta(nueva.getRespuesta());
                emprendimientoParticicipacionComunidadRepository.save(actual);
            } else {
                OpcionesParticipacionComunidad opcion = opcionesParticipacionComunidadRepository.findById(nueva.getOpcionParticipacionId()).orElseThrow();
                EmprendimientoParticipacion nuevaPart = new EmprendimientoParticipacion();
                nuevaPart.setOpcionParticipacion(opcion);
                nuevaPart.setEmprendimiento(emprendimiento);
                nuevaPart.setRespuesta(nueva.getRespuesta());
                emprendimientoParticicipacionComunidadRepository.save(nuevaPart);
            }
        }
        // Actualizar declaraciones finales
        List<EmprendimientoDeclaraciones> actualesDeclaraciones = emprendimientoDeclaracionesRepository.findByEmprendimientoId(id);
        List<EmprendimientoDeclaracionesDTO> nuevasDeclaraciones = emprendimientoRequestDTO.getDeclaracionesFinales() != null ? emprendimientoRequestDTO.getDeclaracionesFinales() : List.of();
        for (EmprendimientoDeclaraciones actual : actualesDeclaraciones) {
            boolean existe = nuevasDeclaraciones.stream().anyMatch(d -> d.getDeclaracionId().equals(actual.getDeclaracion().getId()));
            if (!existe) {
                emprendimientoDeclaracionesRepository.delete(actual);
            }
        }
        for (EmprendimientoDeclaracionesDTO nueva : nuevasDeclaraciones) {
            EmprendimientoDeclaraciones actual = actualesDeclaraciones.stream().filter(d -> d.getDeclaracion().getId().equals(nueva.getDeclaracionId())).findFirst().orElse(null);
            if (actual != null) {
                actual.setAceptada(nueva.getAceptada());
                actual.setNombreFirma(nueva.getNombreFirma());
                actual.setFechaAceptacion(nueva.getFechaAceptacion());
                emprendimientoDeclaracionesRepository.save(actual);
            } else {
                DeclaracionesFinales declaracion = declaracionesFinalesRepository.findById(nueva.getDeclaracionId()).orElseThrow();
                EmprendimientoDeclaraciones nuevaDecl = new EmprendimientoDeclaraciones();
                nuevaDecl.setDeclaracion(declaracion);
                nuevaDecl.setEmprendimiento(emprendimiento);
                nuevaDecl.setAceptada(nueva.getAceptada());
                nuevaDecl.setNombreFirma(nueva.getNombreFirma());
                nuevaDecl.setFechaAceptacion(nueva.getFechaAceptacion());
                emprendimientoDeclaracionesRepository.save(nuevaDecl);
            }
        }

        // Devolver el emprendimiento actualizado
        return obtenerEmprendimientoCompletoPorId(id);
    }

    @Override
    public List<EmprendimientoResponseDTO> obtenerEmprendimientosPorUsuario(Usuarios usuario) {
        List<Emprendimientos> lista = emprendimientosRepository.findByUsuarios(usuario);
        return lista.stream().map(emp -> {
            EmprendimientoResponseDTO dto = EmprendimientoMapper.toResponseDTO(emp);
            dto.setCategorias(EmprendimientoMapper.toCategoriaDTOList(
                emprendimientoCategoriasRepository.findEmprendimientosPorCategoria(emp.getId())
            ));
            dto.setDescripciones(EmprendimientoMapper.toDescripcionDTOList(
                emprendimientosDescripcionRepository.findByEmprendimientoId(emp.getId())
            ));
            dto.setPresenciasDigitales(EmprendimientoMapper.toPresenciaDigitalDTOList(
                emprendimientoPresenciaDigitalRepository.findByEmprendimientoId(emp.getId())
            ));
            dto.setMetricas(EmprendimientoMapper.toMetricasDTOList(
                emprendimientoMetricaRepository.findByEmprendimientoId(emp.getId())
            ));
            dto.setDeclaracionesFinales(EmprendimientoMapper.toDeclaracionesDTOList(
                emprendimientoDeclaracionesRepository.findByEmprendimientoId(emp.getId())
            ));
            dto.setParticipacionesComunidad(EmprendimientoMapper.toParticipacionDTOList(
                emprendimientoParticicipacionComunidadRepository.findByEmprendimientoIdFetchOpcion(emp.getId())
            ));
            dto.setInformacionRepresentante(EmprendimientoMapper.toRepresentanteDTO(
                informacionRepresentanteRepository.findFirstByEmprendimientoId(emp.getId())
            ));
            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    public List<EmprendimientoResponseDTO> obtenerEmprendimientosFiltrado(String nombre, String tipo) {
        // 1. Obtener emprendimientos base
        List<Emprendimientos> lista;
        boolean tieneNombre = nombre != null && !nombre.trim().isEmpty();
        boolean tieneTipo = tipo != null && !tipo.trim().isEmpty();

        if (tieneNombre && tieneTipo) {
            lista = emprendimientosRepository.findByNombreComercialContainingIgnoreCaseAndTiposEmprendimientos_SubTipoContainingIgnoreCase(
                    nombre.trim(), tipo.trim()
            );
        } else if (tieneNombre) {
            lista = emprendimientosRepository.findByNombreComercialContainingIgnoreCase(nombre.trim());
        } else if (tieneTipo) {
            lista = emprendimientosRepository.findByTiposEmprendimientos_SubTipo(tipo.trim());
        } else {
            lista = emprendimientosRepository.findAll();
        }

        if (lista.isEmpty()) {
            return Collections.emptyList();
        }

        // 2. Obtener IDs de emprendimientos
        List<Integer> empIds = lista.stream()
                .map(Emprendimientos::getId)
                .collect(Collectors.toList());

        // 3. Cargar TODAS las relaciones en BATCH (una sola query por tipo)
        Map<Integer, List<EmprendimientoCategorias>> categoriasMap =
                emprendimientoCategoriasRepository.findByEmprendimientoIdIn(empIds).stream()
                        .collect(Collectors.groupingBy(ec -> ec.getEmprendimiento().getId()));

        Map<Integer, List<TiposDescripcionEmprendimiento>> descripcionesMap =
                emprendimientosDescripcionRepository.findByEmprendimientoIdIn(empIds).stream()
                        .collect(Collectors.groupingBy(d -> d.getEmprendimiento().getId()));

        Map<Integer, List<TiposPresenciaDigital>> presenciasMap =
                emprendimientoPresenciaDigitalRepository.findByEmprendimientoIdIn(empIds).stream()
                        .collect(Collectors.groupingBy(p -> p.getEmprendimiento().getId()));

        Map<Integer, List<EmprendimientoMetricas>> metricasMap =
                emprendimientoMetricaRepository.findByEmprendimientoIdIn(empIds).stream()
                        .collect(Collectors.groupingBy(m -> m.getEmprendimiento().getId()));

        Map<Integer, List<EmprendimientoDeclaraciones>> declaracionesMap =
                emprendimientoDeclaracionesRepository.findByEmprendimientoIdIn(empIds).stream()
                        .collect(Collectors.groupingBy(d -> d.getEmprendimiento().getId()));

        Map<Integer, List<EmprendimientoParticipacion>> participacionesMap =
                emprendimientoParticicipacionComunidadRepository.findByEmprendimientoIdIn(empIds).stream()
                        .collect(Collectors.groupingBy(p -> p.getEmprendimiento().getId()));

        Map<Integer, InformacionRepresentante> representantesMap =
                informacionRepresentanteRepository.findByEmprendimientoIdIn(empIds).stream()
                        .collect(Collectors.toMap(r -> r.getEmprendimiento().getId(), r -> r));

        // 4. Mapear todo usando los Maps precargados
        return lista.stream().map(emp -> {
            EmprendimientoResponseDTO dto = EmprendimientoMapper.toResponseDTO(emp);

            dto.setCategorias(EmprendimientoMapper.toCategoriaDTOList(
                    categoriasMap.getOrDefault(emp.getId(), Collections.emptyList())
            ));
            dto.setDescripciones(EmprendimientoMapper.toDescripcionDTOList(
                    descripcionesMap.getOrDefault(emp.getId(), Collections.emptyList())
            ));
            dto.setPresenciasDigitales(EmprendimientoMapper.toPresenciaDigitalDTOList(
                    presenciasMap.getOrDefault(emp.getId(), Collections.emptyList())
            ));
            dto.setMetricas(EmprendimientoMapper.toMetricasDTOList(
                    metricasMap.getOrDefault(emp.getId(), Collections.emptyList())
            ));
            dto.setDeclaracionesFinales(EmprendimientoMapper.toDeclaracionesDTOList(
                    declaracionesMap.getOrDefault(emp.getId(), Collections.emptyList())
            ));
            dto.setParticipacionesComunidad(EmprendimientoMapper.toParticipacionDTOList(
                    participacionesMap.getOrDefault(emp.getId(), Collections.emptyList())
            ));
            dto.setInformacionRepresentante(EmprendimientoMapper.toRepresentanteDTO(
                    representantesMap.get(emp.getId())
            ));

            return dto;
        }).collect(Collectors.toList());
    }


}
