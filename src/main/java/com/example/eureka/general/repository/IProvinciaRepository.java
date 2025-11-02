package com.example.eureka.general.repository;

import com.example.eureka.domain.model.Provincias;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IProvinciaRepository extends JpaRepository<Provincias,Integer> {
}
