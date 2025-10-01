package vn.tayjava.service;

import org.springframework.security.core.userdetails.UserDetails;
import vn.tayjava.util.TokenType;

import java.util.Map;

public interface JwtService {

    String generateToken(UserDetails user);

    String extractUsername(String token, TokenType type);

    boolean isTokenValid(String token,TokenType type, UserDetails user);

    String generateRefreshToken(UserDetails user);

}