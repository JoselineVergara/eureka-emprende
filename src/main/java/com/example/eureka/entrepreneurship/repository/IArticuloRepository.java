package com.example.eureka.entrepreneurship.repository;

import com.example.eureka.enums.EstadoArticulo;
import com.example.eureka.model.ArticulosBlog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IArticuloRepository extends JpaRepository<ArticulosBlog, Integer> {

    @Query("SELECT a FROM ArticulosBlog a JOIN a.tags t WHERE t.idTag = :idTag")
    List<ArticulosBlog> findByTagId(@Param("idTag") Integer idTag);

}
