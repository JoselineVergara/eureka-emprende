package com.example.eureka.security;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Profile(value = {"dev", "qa", "prod"})
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final UserDetailsService jwtUserDetailsService;
    private final JwtRequestFilter jwtRequestFilter;

    // 🔹 Variables desde application.properties (o variables de entorno en Render)
    @Value("${env.allow-origins-cors.value}")
    private String allowedOrigins;

    @Value("${env.allow-origins-headers-credentials.value}")
    private String allowedHeaders;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(jwtUserDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                // ✅ HABILITAR CORS usando el bean de abajo
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .authorizeHttpRequests(req -> req
                        .requestMatchers("/v1/auth/login",
                                "/v1/auth/register",
                                "/api/public/**",
                                "/v1/categorias",
                                "/v1/categorias/{id}",
                                "/v1/auth/test-bcrypt",
                                "/v1/auth/rehash-password",
                                "/v1/provincia",
                                "/v1/ciudad/provincia/{id}",
                                "/v1/eventos/emprendimiento/{idEmprendimiento}",
                                "/v1/eventos/{idEvento}",
                                "/v1/eventos/filtrar",
                                "/v1/blog/articulos",
                                "/v1/blog/articulos/tag/{idTag}",
                                "/v1/blog/articulos/{idArticulo}",
                                "/v1/blog/tags",
                                "/v1/emprendimientos",
                                "/v1/emprendimientos/categoria/{categoriaId}",
                                "/v1/emprendimientos",
                                "/v1/emprendimientos/{id}/publico",
                                "/v1/emprendimientos/filtrar")
                        .permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .exceptionHandling(e -> e.authenticationEntryPoint(jwtAuthenticationEntryPoint));

        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    // ✅ CONFIGURACIÓN CORS COMPLETA
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();

        // Permitir múltiples orígenes separados por coma
        config.setAllowedOrigins(Arrays.asList(allowedOrigins.split(",")));

        // Métodos HTTP permitidos
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));

        // Headers permitidos
        config.setAllowedHeaders(Arrays.asList(allowedHeaders.split(",")));

        // Permitir credenciales (cookies, JWT)
        config.setAllowCredentials(true);

        // Registrar configuración global
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return source;
    }
}
