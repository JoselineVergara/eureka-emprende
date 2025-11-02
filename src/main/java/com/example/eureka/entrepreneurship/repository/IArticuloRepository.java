package com.example.eureka.entrepreneurship.repository;

import com.example.eureka.enums.EstadoArticulo;
import com.example.eureka.model.ArticulosBlog;
import com.example.eureka.model.TagsBlog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface IArticuloRepository extends JpaRepository<ArticulosBlog, Integer>,
        JpaSpecificationExecutor<ArticulosBlog> {

}