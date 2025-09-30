package com.example.eureka.security;

import com.example.eureka.model.Usuarios;
import com.example.eureka.auth.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

//Clase S2
@Service
@RequiredArgsConstructor
public class JwtUserDetailsService implements UserDetailsService {

    private final IUserRepository repo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuarios usuarios = repo.findUsuariosByCorreo(username);

        if(usuarios == null) {
            throw new UsernameNotFoundException("User not found: " + username);
        }

        List<GrantedAuthority> roles = new ArrayList<>();
        String rol = "ADMIN";
        roles.add(new SimpleGrantedAuthority(rol));

        return new org.springframework.security.core.userdetails.User(usuarios.getContrasena(), usuarios.getContrasena(), roles);
    }
}
