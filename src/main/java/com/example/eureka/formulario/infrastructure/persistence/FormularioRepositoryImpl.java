package com.example.eureka.formulario.infrastructure.persistence;

import com.example.eureka.formulario.domain.model.Formulario;
import com.example.eureka.formulario.port.out.IFormularioRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class FormularioRepositoryImpl implements IFormularioRepository {

    private final FormularioJpaRepository jpaRepository;

    public FormularioRepositoryImpl(FormularioJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Optional<Formulario> findByTipoFormularioNombre(String tipoNombre) {
        return jpaRepository.findByTipoFormularioNombre(tipoNombre);
    }

    @Override
    public Optional<Formulario> findById(Long id) {
        return jpaRepository.findById(id);
    }
}