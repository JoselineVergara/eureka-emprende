package com.example.eureka.auth.repository;

import com.example.eureka.model.Usuarios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepository extends JpaRepository<Usuarios, Integer> {

    @Query("SELECT u FROM Usuarios u WHERE u.correo = :correo")
    Usuarios obtenerUsuarioCorreo(@Param("correo") String correo);

    @Query("SELECT u.id FROM Usuarios u WHERE u.correo = :correo")
    Integer obtenerIdPorCorreo(@Param("correo") String correo);

    Usuarios findByCorreo(String correo);
}
