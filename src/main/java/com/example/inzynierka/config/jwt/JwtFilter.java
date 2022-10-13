package com.example.inzynierka.config.jwt;

import com.example.inzynierka.services.implementations.AccountServiceImpl;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {
    private final JwtTokenService jwtTokenService;
    private final AccountServiceImpl accountService;

    public JwtFilter(JwtTokenService jwtTokenService, AccountServiceImpl accountService) {
        this.jwtTokenService = jwtTokenService;
        this.accountService = accountService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        final String header = request.getHeader("Authorization");

        String login = null;
        String token;

        if(header != null && header.startsWith("Bearer ")){
            token = header.substring(7);
            login = jwtTokenService.getLoginFromToken(token);
        }

        if(login != null){
            UserDetails userDetails = accountService.loadUserByUsername(login);
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        }

        filterChain.doFilter(request, response);
    }
}
