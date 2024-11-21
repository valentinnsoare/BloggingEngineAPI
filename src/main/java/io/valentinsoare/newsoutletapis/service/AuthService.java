package io.valentinsoare.newsoutletapis.service;

import io.valentinsoare.newsoutletapis.dto.LoginDto;
import io.valentinsoare.newsoutletapis.dto.RegisterDto;

public interface AuthService {
    String login(LoginDto loginDto);
    String register(RegisterDto registerDto);
}
