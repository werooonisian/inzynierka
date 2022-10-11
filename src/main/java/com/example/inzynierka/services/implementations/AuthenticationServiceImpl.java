package com.example.inzynierka.services.implementations;

import com.example.inzynierka.config.jwt.JwtResponse;
import com.example.inzynierka.config.jwt.JwtTokenService;
import com.example.inzynierka.models.AuthRequest;
import com.example.inzynierka.services.AccountService;
import com.example.inzynierka.services.AuthenticationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenService jwtTokenService;
    private final AccountService accountService;

    public AuthenticationServiceImpl(
            AuthenticationManager authenticationManager,
            JwtTokenService jwtTokenService,
            AccountService accountService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenService = jwtTokenService;
        this.accountService = accountService;
    }

    @Override
    public JwtResponse authenticate(AuthRequest authRequest) throws Exception {
        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getLogin(), authRequest.getPassword()));
            UserDetails userDetails = accountService.loadUserByUsername(authRequest.getLogin());
            Map<String, Object> claims = jwtTokenService.buildClaims(userDetails);
            String token = jwtTokenService.generateToken(claims, authRequest.getLogin());
            log.info("Logged in: " + SecurityContextHolder.getContext().getAuthentication().getName());
            return new JwtResponse(token);
        }catch (BadCredentialsException e){
            throw new Exception("Bad credentials", e);
        }
    }

}
