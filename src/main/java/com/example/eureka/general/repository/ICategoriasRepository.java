package com.example.eureka.general.repository;

import com.example.eureka.model.Categorias;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICategoriasRepository extends JpaRepository<Categorias,Integer> {

}
