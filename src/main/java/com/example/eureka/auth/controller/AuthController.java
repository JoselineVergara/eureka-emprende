package com.example.eureka.auth.controller;

import com.example.eureka.auth.dto.UserDTO;
import com.example.eureka.auth.dto.UsuarioEmprendeDTO;
import com.example.eureka.security.JwtRequest;
import com.example.eureka.security.JwtResponse;
import com.example.eureka.security.JwtTokenUtil;
import com.example.eureka.auth.service.IAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    @Autowired
    private final IAuthService authService;

    @Autowired
    private final AuthenticationManager authenticationManager;

    @Autowired
    private final JwtTokenUtil jwtTokenUtil;

    @Autowired
    private final UserDetailsService userDetailsService;


    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest req) throws Exception {
        authenticate(req.getEmail(), req.getPassword());

        final UserDetails user = userDetailsService.loadUserByUsername((req.getEmail()));
        final String token = jwtTokenUtil.generateToken(user);

        return ResponseEntity.ok(new JwtResponse(token));
    }

    @GetMapping("/validateToken")
    public ResponseEntity<UserDTO> validateToken() {
        return ResponseEntity.ok().build();
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UsuarioEmprendeDTO usuario) throws Exception {
        authService.createUser(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    private void authenticate(String username, String password) throws Exception{
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}
