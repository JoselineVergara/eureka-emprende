package com.example.eureka.entrepreneurship.repository;

import com.example.eureka.model.Categorias;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICategoriaRepository extends JpaRepository<Categorias, Integer> {
}