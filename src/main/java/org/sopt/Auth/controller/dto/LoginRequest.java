package org.sopt.Auth.controller.dto;

public record LoginRequest(
        String email,
        String password
) {}
