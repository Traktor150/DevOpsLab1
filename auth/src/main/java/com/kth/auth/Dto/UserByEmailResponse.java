package com.kth.auth.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserByEmailResponse {
    Long id;
    String password;
    String name;
    String email;
    String role;
}