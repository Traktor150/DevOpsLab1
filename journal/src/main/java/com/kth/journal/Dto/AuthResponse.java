package com.kth.journal.Dto;

public record AuthResponse(String email, String name, String role, String token) {
}
