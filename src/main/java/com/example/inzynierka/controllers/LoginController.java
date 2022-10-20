package com.example.inzynierka.controllers;

import com.example.inzynierka.config.jwt.JwtResponse;
import com.example.inzynierka.models.AuthRequest;
import com.example.inzynierka.services.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class LoginController {
    private final AuthenticationService authenticationService;

    public LoginController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody AuthRequest authRequest) throws Exception{
        return ResponseEntity.ok(authenticationService.authenticate(authRequest));
    }

    @GetMapping("/test")
    public String testAutoryzji(){
        return SecurityContextHolder.getContext().getAuthentication().getName() + " wszystko ok";
    }
}