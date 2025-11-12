package com.example.eureka.articulo.infrastructure.persistence;

import com.example.eureka.articulo.domain.model.ArticulosBlog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
interface ArticuloJpaRepository extends JpaRepository<ArticulosBlog, Integer>,
        JpaSpecificationExecutor<ArticulosBlog> {  }