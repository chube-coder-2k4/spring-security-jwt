package vn.tayjava.service;

import org.springframework.security.core.userdetails.UserDetails;
import vn.tayjava.util.TokenType;

import java.util.Map;

public interface JwtService {

    String generateToken(UserDetails user);

}