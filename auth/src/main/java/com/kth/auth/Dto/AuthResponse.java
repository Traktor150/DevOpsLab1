package com.kth.auth.Dto;

public record AuthResponse(Long id, String email, String name, String role, String token) {
}
