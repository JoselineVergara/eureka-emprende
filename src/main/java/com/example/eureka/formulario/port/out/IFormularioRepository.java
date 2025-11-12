package com.example.eureka.formulario.port.out;

import com.example.eureka.formulario.domain.model.Formulario;
import java.util.Optional;

public interface IFormularioRepository {
    Optional<Formulario> findByTipoFormularioNombre(String tipoNombre);
    Optional<Formulario> findById(Long id);
}
