package com.example.eureka.auth.infrastructure.persistence;

import com.example.eureka.auth.domain.Usuarios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

interface UserJpaRepository extends JpaRepository<Usuarios, Integer> {

    @Query("SELECT u FROM Usuarios u WHERE u.correo = :correo")
    Usuarios obtenerUsuarioCorreo(@Param("correo") String correo);

    @Query("SELECT u.id FROM Usuarios u WHERE u.correo = :correo")
    Integer obtenerIdPorCorreo(@Param("correo") String correo);

    Usuarios findByCorreo(String correo);

    List<Usuarios> findAllByRol_Nombre(String rolNombre);
}