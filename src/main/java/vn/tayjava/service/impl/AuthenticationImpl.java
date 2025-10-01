package vn.tayjava.service.impl;

import io.micrometer.common.util.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import vn.tayjava.dto.request.SigninRequest;
import vn.tayjava.dto.request.TokenResponse;
import vn.tayjava.exception.InvalidDataException;
import vn.tayjava.exception.UserNotFoundException;
import vn.tayjava.repository.UserRepository;
import vn.tayjava.service.AuthenticationService;
import vn.tayjava.service.JwtService;

@Service
@RequiredArgsConstructor
public class AuthenticationImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Override
    public TokenResponse authenticate(SigninRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        var user = userRepository.findByUsername(request.getUsername()).orElseThrow(() -> new UserNotFoundException("User not found"));
        return TokenResponse.builder()
                .accessToken(jwtService.generateToken(user))
                .refreshToken(jwtService.generateRefreshToken(user))
                .userId(1L)
                .build();
    }

    @Override
    public TokenResponse refresh(HttpServletRequest request) {
        String token = request.getHeader("x-refresh-token");
        if(StringUtils.isBlank(token)){
            throw new InvalidDataException("Invalid refresh token must be not blank");
        }

        final String userName = jwtService.extractUsername(token);
        System.out.println(userName);
        return null;
    }


}
