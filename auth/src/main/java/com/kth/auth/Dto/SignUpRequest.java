package com.kth.auth.Dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SignUpRequest {
    @NotBlank
    private String password;

    @NotBlank
    private String name;

    @Email
    private String email;
}
