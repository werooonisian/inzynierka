package com.example.inzynierka.controllers;

import com.example.inzynierka.config.jwt.JwtResponse;
import com.example.inzynierka.payload.AuthRequest;
import com.example.inzynierka.services.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
@CrossOrigin
public class LoginController {
    private final AuthenticationService authenticationService;

    public LoginController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping
    public ResponseEntity<JwtResponse> login(@RequestBody AuthRequest authRequest) throws Exception{
        return ResponseEntity.ok(authenticationService.authenticate(authRequest));
    }
}
