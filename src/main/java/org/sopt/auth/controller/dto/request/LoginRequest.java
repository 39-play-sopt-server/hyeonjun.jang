package org.sopt.auth.controller.dto.request;

public record LoginRequest(
        String email,
        String password
) {}
