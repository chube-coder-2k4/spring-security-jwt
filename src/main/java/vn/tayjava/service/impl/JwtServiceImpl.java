package vn.tayjava.service.impl;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import vn.tayjava.service.JwtService;
import vn.tayjava.util.TokenType;

@Service
public class JwtServiceImpl implements JwtService {
    @Override
    public String generateToken(UserDetails user) {
        return "";
    }

    @Override
    public String generateRefreshToken(UserDetails user) {
        return "";
    }

    @Override
    public String extractUsername(String token, TokenType type) {
        return "";
    }

    @Override
    public boolean isValid(String token, TokenType type, UserDetails user) {
        return false;
    }
}
