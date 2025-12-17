package com.example.eureka.metricas.application.service;

import com.example.eureka.entrepreneurship.domain.model.EmprendimientoCategorias;
import com.example.eureka.entrepreneurship.domain.model.Emprendimientos;
import com.example.eureka.entrepreneurship.port.out.ICategoriaRepository;
import com.example.eureka.entrepreneurship.port.out.IEmprendimientoCategoriasRepository;
import com.example.eureka.entrepreneurship.port.out.IEmprendimientosRepository;
import com.example.eureka.general.domain.model.Categorias;
import com.example.eureka.general.infrastructure.dto.CategoriasDTO;
import com.example.eureka.metricas.domain.MetricasGenerales;
import com.example.eureka.metricas.domain.MetricasPregunta;
import com.example.eureka.metricas.infrastructure.dto.MetricaPreguntaDTO;
import com.example.eureka.metricas.infrastructure.dto.MetricasGeneralesDTO;
import com.example.eureka.metricas.port.in.MetricasGeneralesService;
import com.example.eureka.metricas.port.out.IMetricasGeneralesRepository;
import com.example.eureka.metricas.port.out.IMetricasPreguntaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MetricasGeneralesServiceImpl implements MetricasGeneralesService {

    private final IMetricasGeneralesRepository metricasGeneralesRepository;
    private final IEmprendimientosRepository emprendimientosRepository;
    private final IEmprendimientoCategoriasRepository emprendimientoCategoriasRepository;
    private final ICategoriaRepository categoriaRepository;
    private final IMetricasPreguntaRepository  metricasPreguntaRepository;



    @Override
    public MetricasGeneralesDTO findTopByOrderByVistasDesc() {
        MetricasGenerales metricasGenerales = metricasGeneralesRepository.findTopByOrderByVistasDesc().orElse(null);
        return toDTO(metricasGenerales);
    }

    @Override
    public MetricasGeneralesDTO findTopByOrderByVistasAsc() {
        MetricasGenerales metricasGenerales = metricasGeneralesRepository.findTopByOrderByVistasAsc().orElse(null);
        return toDTO(metricasGenerales);
    }

    @Override
    public MetricaPreguntaDTO findTopByOrderByNivelValoracionDesc() {
        MetricasPregunta metricasPregunta = metricasPreguntaRepository.findTopByOrderByValoracionDesc();
        return metricaPreguntaDTO(metricasPregunta);
    }

    @Override
    public MetricaPreguntaDTO findTopByOrderByNivelValoracionAsc() {
        MetricasPregunta metricasPregunta = metricasPreguntaRepository.findTopByOrderByValoracionAsc();
        return metricaPreguntaDTO(metricasPregunta);
    }

    @Override
    public MetricasGeneralesDTO save(MetricasGeneralesDTO metricasGenerales) {
        MetricasGenerales metricasGenerales1 = metricasGeneralesRepository.save(toEntity(metricasGenerales));
        return toDTO(metricasGenerales1);
    }

    @Override
    public MetricasGeneralesDTO findById(Integer id) {
        MetricasGenerales metricasGenerales = metricasGeneralesRepository.findById(id).orElse(null);
        return toDTO(metricasGenerales);
    }

    @Override
    public MetricasGeneralesDTO findByIdEmprendimiento(Integer id) {
        Emprendimientos emp = emprendimientosRepository.findById(id).orElse(null);
        MetricasGenerales metricasGenerales1 = metricasGeneralesRepository.findById(id).orElse(null);
        return toDTO(metricasGenerales1);
    }

    @Override
    public HashMap<String, Object> findTopByOrderByVistasCategoriaDesc() {
        MetricasGenerales metricasGenerales = metricasGeneralesRepository.findTopByOrderByVistasDesc().orElse(null);
        Emprendimientos emp = metricasGenerales.getEmprendimientos();
        List<EmprendimientoCategorias> emprendimientoCategorias = emprendimientoCategoriasRepository.findByEmprendimientoId(emp.getId());
        Categorias categorias = null;
        if(null != emprendimientoCategorias) {
            categorias = emprendimientoCategorias.get(0).getCategoria();
        }
        CategoriasDTO cat = new CategoriasDTO();
        cat.setId(categorias.getId());
        cat.setDescripcion(categorias.getDescripcion());
        cat.setNombre(categorias.getNombre());
        cat.setIdMultimedia(categorias.getMultimedia().getId());
        cat.setUrlImagen(categorias.getMultimedia().getUrlArchivo());
        HashMap<String, Object> resultado = new HashMap<>();
        MetricasGeneralesDTO me = toDTO(metricasGenerales);
        resultado.put("categorias", cat);
        if(categorias != null) {
            resultado.put("vistas", me.getVistas());
        }



        return resultado;
    }

    @Override
    public List<MetricasGeneralesDTO> findAllByFechaRegistroIsBetweenOrEmprendimientos(LocalDateTime fechaRegistroAfter, LocalDateTime fechaRegistroBefore, Integer  idEmprendimientos) {
        Emprendimientos emprendimientos = emprendimientosRepository.findById(idEmprendimientos).orElse(null);
        List<MetricasGenerales> ls = metricasGeneralesRepository.findAllByFechaRegistroIsBetweenOrEmprendimientos(fechaRegistroAfter, fechaRegistroBefore, emprendimientos);
        List<MetricasGeneralesDTO> resultado = new ArrayList<>();
        for(MetricasGenerales metricasGenerales : ls) {
            resultado.add(toDTO(metricasGenerales));
        }
        return resultado;
    }

    MetricasGeneralesDTO toDTO(MetricasGenerales metricasGenerales) {
        MetricasGeneralesDTO metricasGeneralesDTO = new MetricasGeneralesDTO();
        metricasGeneralesDTO.setId(metricasGenerales.getId());
        metricasGeneralesDTO.setVistas(metricasGenerales.getVistas());
        metricasGeneralesDTO.setIdEmprendimiento(metricasGenerales.getEmprendimientos().getId());
        metricasGeneralesDTO.setFechaRegistro(metricasGenerales.getFechaRegistro());
        return  metricasGeneralesDTO;
    }

    MetricaPreguntaDTO metricaPreguntaDTO(MetricasPregunta metricasPregunta) {
        MetricaPreguntaDTO metricaPreguntaDTO = new MetricaPreguntaDTO();
        metricaPreguntaDTO.setId(metricasPregunta.getId());
        metricaPreguntaDTO.setIdPregunta(metricasPregunta.getPregunta().getIdPregunta().intValue());
        metricaPreguntaDTO.setValoracion(metricasPregunta.getValoracion());
        metricaPreguntaDTO.setFechaRegistro(metricasPregunta.getFechaRegistro());
        return metricaPreguntaDTO;
    }

    MetricasGenerales toEntity(MetricasGeneralesDTO metricasGeneralesDTO) {
        Emprendimientos emp = emprendimientosRepository.findById(metricasGeneralesDTO.getIdEmprendimiento()).orElse(null);
        MetricasGenerales metricasGenerales = new MetricasGenerales();
        metricasGenerales.setId(metricasGeneralesDTO.getId());
        metricasGenerales.setVistas(metricasGeneralesDTO.getVistas());
        metricasGenerales.setEmprendimientos(emp);
        metricasGenerales.setFechaRegistro(metricasGeneralesDTO.getFechaRegistro());
        return metricasGenerales;
    }
}
