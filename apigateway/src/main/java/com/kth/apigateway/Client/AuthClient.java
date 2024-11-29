package com.kth.apigateway.Client;

import com.kth.apigateway.Dto.UserByEmailResponse;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;

public interface AuthClient {
    @GetExchange("/api/auth/byEmail")
    UserByEmailResponse gerUserByEmail(@RequestParam String email);
}
