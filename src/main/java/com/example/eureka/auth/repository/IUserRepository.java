package com.example.eureka.auth.repository;

import com.example.eureka.model.Usuarios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepository extends JpaRepository<Usuarios, Integer> {
    Usuarios findUsuariosByCorreo(String correo);
}
