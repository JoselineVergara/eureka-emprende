package com.example.eureka.general.repository;

import com.example.eureka.model.Categorias;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICategoriasRepository extends JpaRepository<Categorias,Integer> {

    @Query("SELECT c FROM Categorias as c")
    List<Categorias> findAll();

}
