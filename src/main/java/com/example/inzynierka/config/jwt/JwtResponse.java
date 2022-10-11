package com.example.inzynierka.config.jwt;

public class JwtResponse {
    private String jwtToken;

    public JwtResponse(String jwtToken){
        this.jwtToken = jwtToken;
    }

    public String getJwtToken() {
        return jwtToken;
    }
}
