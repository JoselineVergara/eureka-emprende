package com.example.eureka.entrepreneurship.repository;

import com.example.eureka.domain.model.ArticulosBlog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface IArticuloRepository extends JpaRepository<ArticulosBlog, Integer>,
        JpaSpecificationExecutor<ArticulosBlog> {

}