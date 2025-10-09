package com.example.eureka.general.repository;

import com.example.eureka.model.MetricasBasicas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITiposMetricasRepository extends JpaRepository<MetricasBasicas,Integer> {
}
