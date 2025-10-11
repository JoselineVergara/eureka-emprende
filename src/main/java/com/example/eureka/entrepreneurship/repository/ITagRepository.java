package com.example.eureka.entrepreneurship.repository;

import com.example.eureka.model.TagsBlog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ITagRepository extends JpaRepository<TagsBlog, Integer> {

    Optional<TagsBlog> findByNombre(String nombre);
}