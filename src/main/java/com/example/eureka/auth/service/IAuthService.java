package com.example.eureka.auth.service;

import com.example.eureka.auth.dto.UsuarioEmprendeDTO;
import com.example.eureka.domain.model.Usuarios;

public interface IAuthService {

    public Usuarios createUser(UsuarioEmprendeDTO usuario) throws Exception;
}
