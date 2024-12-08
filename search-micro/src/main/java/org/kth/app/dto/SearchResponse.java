package org.kth.app.dto;

public record SearchResponse(Long id, Long accountId, String name, String email, String role) {
}