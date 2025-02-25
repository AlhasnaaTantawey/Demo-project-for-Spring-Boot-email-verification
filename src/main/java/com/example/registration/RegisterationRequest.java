package com.example.registration;

public record RegisterationRequest(
        String firstName,
        String lastName,
        String email,
        String password,
        String role) {
}
