package com.example.eureka.general.service;

import com.example.eureka.general.dto.ProvinciaDTO;
import java.util.List;

public interface IProvinciaService {

    Integer crearProvincia(ProvinciaDTO provinciaDTO);

    Integer actualizarProvincia(Integer id, ProvinciaDTO provinciaDTO);

    void eliminarProvincia(Integer id);

    List<ProvinciaDTO> obtenerProvincias();

    ProvinciaDTO obtenerProvinciaPorId(Integer id);
}
