package com.example.eureka.metricas.application.service;

import com.example.eureka.entrepreneurship.domain.model.EmprendimientoCategorias;
import com.example.eureka.entrepreneurship.domain.model.Emprendimientos;
import com.example.eureka.entrepreneurship.port.out.ICategoriaRepository;
import com.example.eureka.entrepreneurship.port.out.IEmprendimientoCategoriasRepository;
import com.example.eureka.entrepreneurship.port.out.IEmprendimientosRepository;
import com.example.eureka.general.domain.model.Categorias;
import com.example.eureka.metricas.domain.MetricasGenerales;
import com.example.eureka.metricas.domain.MetricasPregunta;
import com.example.eureka.metricas.port.in.MetricasGeneralesService;
import com.example.eureka.metricas.port.out.IMetricasGeneralesRepository;
import com.example.eureka.metricas.port.out.IMetricasPreguntaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

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
    public MetricasGenerales findTopByOrderByVistasDesc() {
        return metricasGeneralesRepository.findTopByOrderByVistasDesc().orElse(null);
    }

    @Override
    public MetricasGenerales findTopByOrderByVistasAsc() {
        return metricasGeneralesRepository.findTopByOrderByVistasAsc().orElse(null);
    }

    @Override
    public MetricasPregunta findTopByOrderByNivelValoracionDesc() {
        return metricasPreguntaRepository.findTopByOrderByValoracionDesc();
    }

    @Override
    public MetricasPregunta findTopByOrderByNivelValoracionAsc() {
        return metricasPreguntaRepository.findTopByOrderByValoracionAsc();
    }

    @Override
    public MetricasGenerales save(MetricasGenerales metricasGenerales) {
        return metricasGeneralesRepository.save(metricasGenerales);
    }

    @Override
    public MetricasGenerales findById(Integer id) {
        return metricasGeneralesRepository.findById(id).orElse(null);
    }

    @Override
    public MetricasGenerales findByIdEmprendimiento(Integer id) {
        Emprendimientos emp = emprendimientosRepository.findById(id).orElse(null);
        return metricasGeneralesRepository.findByEmprendimientos(emp).orElse(null);
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
        HashMap<String, Object> resultado = new HashMap<>();
        resultado.put("categorias", categorias);
        if(categorias != null) {
            resultado.put("vistas", metricasGenerales.getVistas());
        }



        return resultado;
    }

    @Override
    public List<MetricasGenerales> findAllByFechaRegistroIsBetweenOrEmprendimientos(LocalDateTime fechaRegistroAfter, LocalDateTime fechaRegistroBefore, Integer  idEmprendimientos) {
        Emprendimientos emprendimientos = emprendimientosRepository.findById(idEmprendimientos).orElse(null);
        return metricasGeneralesRepository.findAllByFechaRegistroIsBetweenOrEmprendimientos(fechaRegistroAfter, fechaRegistroBefore, emprendimientos);
    }
}
