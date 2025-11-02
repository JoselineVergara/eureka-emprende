package com.example.eureka.entrepreneurship.repository;

import com.example.eureka.domain.model.TagsBlog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ITagRepository extends JpaRepository<TagsBlog, Integer> {

    Optional<TagsBlog> findByNombre(String nombre);

    @Query("SELECT t FROM TagsBlog t WHERE LOWER(t.nombre) IN :nombres")
    List<TagsBlog> findAllByNombreInIgnoreCase(@Param("nombres") List<String> nombres);

}