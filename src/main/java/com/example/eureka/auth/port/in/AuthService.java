package com.example.eureka.auth.port.in;

import com.example.eureka.auth.infrastructure.dto.UsuarioEmprendeDTO;
import com.example.eureka.auth.domain.Usuarios;

public interface AuthService {

    public Usuarios createUser(UsuarioEmprendeDTO usuario) throws Exception;
}
