package com.example.inzynierka.services;

import com.example.inzynierka.config.jwt.JwtResponse;
import com.example.inzynierka.payload.AuthRequest;

public interface AuthenticationService {
    JwtResponse authenticate(AuthRequest authRequest) throws Exception;
}
