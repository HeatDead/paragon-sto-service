package com.example.paragonstoservice.Services;

public interface TokenService {
    boolean checkToken(String token);
    String checkRole(String token);
}
