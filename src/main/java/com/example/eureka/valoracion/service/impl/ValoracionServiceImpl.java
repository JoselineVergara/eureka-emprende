package com.example.eureka.valoracion.service.impl;

import com.example.eureka.domain.model.Emprendimientos;
import com.example.eureka.domain.model.Respuesta;
import com.example.eureka.valoracion.dto.EmprendimientoInfo;
import com.example.eureka.valoracion.dto.RespuestaFormularioDTO;
import com.example.eureka.valoracion.repository.IValoracionRepository;
import com.example.eureka.valoracion.service.ValoracionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ValoracionServiceImpl implements ValoracionService {

    private final IValoracionRepository valoracionRepository;


    @Override
    public List<Respuesta> findAllByEmprendimientos(Emprendimientos emprendimientos) {
        return valoracionRepository.findAllByEmprendimientos(emprendimientos);
    }

    @Override
    public List<EmprendimientoInfo> obtenerEmprendimientos() {
        return valoracionRepository.obtenerEmprendimientos();
    }

    @Override
    public boolean existsByEmprendimientos(Emprendimientos emprendimientos) {
        return valoracionRepository.existsByEmprendimientos(emprendimientos);
    }

    @Override
    public List<RespuestaFormularioDTO> obtenerRespuestasPorEmprendimiento(Long idEmprendimiento) {
        return valoracionRepository.obtenerRespuestasPorEmprendimiento(idEmprendimiento);
    }
}
