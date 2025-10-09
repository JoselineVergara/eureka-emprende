package com.example.eureka.general.repository;

import com.example.eureka.model.Multimedia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IMultimediaRepository extends JpaRepository<Multimedia,Integer> {
}
