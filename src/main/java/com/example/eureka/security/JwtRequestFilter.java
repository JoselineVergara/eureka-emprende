package com.example.eureka.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@Profile(value = {"dev", "qa", "prod"})
@Component
@RequiredArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {

    private final JwtUserDetailsService jwtUserDetailsService;
    private final JwtTokenUtil jwtTokenUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String header = request.getHeader(HttpHeaders.AUTHORIZATION); //"Authorization"

        String username = null;
        String jwtToken = null;

        //Bearer 11122233344
        if (header != null && header.startsWith("Bearer ")) {
            final int TOKEN_POSITION = 1;
            //jwtToken = header.substring(7);
            jwtToken = header.split(" ")[TOKEN_POSITION];

            try{
                username = jwtTokenUtil.getUsernameFromToken(jwtToken);
            }catch (Exception e){
                request.setAttribute("error", e.getMessage());
            }
        }

        if(username != null && jwtToken != null) {
            UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(username);

            if(jwtTokenUtil.validateToken(jwtToken, userDetails)) {
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }

        filterChain.doFilter(request, response);
    }
}
