package com.example.eureka.auth.controller;

import com.example.eureka.auth.dto.UserDTO;
import com.example.eureka.auth.dto.UsuarioEmprendeDTO;
import com.example.eureka.auth.service.IAuthService;
import com.example.eureka.security.JwtRequest;
import com.example.eureka.security.JwtResponse;
import com.example.eureka.security.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final IAuthService authService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest req) throws Exception {
        // autenticación manual
        authenticate(req.getEmail(), req.getPassword());

        // cargar usuario
        final UserDetails user = userDetailsService.loadUserByUsername(req.getEmail());

        // generar token JWT
        final String token = jwtTokenUtil.generateToken(user);

        return ResponseEntity.ok(new JwtResponse(token));
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UsuarioEmprendeDTO usuario) throws Exception {
        usuario.setContrasena(passwordEncoder.encode(usuario.getContrasena()));
        authService.createUser(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/validateToken")
    public ResponseEntity<UserDTO> validateToken() {
        return ResponseEntity.ok().build();
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }

    @GetMapping("/test-bcrypt")
    public ResponseEntity<String> testBcrypt() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        String passwordPlano = "clave123";
        String passwordHasheadaEnBD = "$2a$10$Is/iczB4Pr5eN0E4ll6e2.eAlbdL1uMu/Xq05w1BwK78R4xZYvGLO";

        boolean matches = encoder.matches(passwordPlano, passwordHasheadaEnBD);

        return ResponseEntity.ok("¿Contraseña coincide? " + matches);
    }

    @PostMapping("/rehash-password")
    public ResponseEntity<String> rehashPassword(@RequestParam String email, @RequestParam String plainPassword) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String newHash = encoder.encode(plainPassword);

        return ResponseEntity.ok("Nueva contraseña hasheada: " + newHash);
    }
}
