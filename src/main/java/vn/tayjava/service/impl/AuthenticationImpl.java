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
import vn.tayjava.model.Token;
import vn.tayjava.model.User;
import vn.tayjava.repository.UserRepository;
import vn.tayjava.service.AuthenticationService;
import vn.tayjava.service.JwtService;
import vn.tayjava.service.TokenService;
import vn.tayjava.util.TokenType;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final TokenService tokenService;

    @Override
    public TokenResponse authenticate(SigninRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        var user = userRepository.findByUsername(request.getUsername()).orElseThrow(() -> new UserNotFoundException("User not found"));
        String accessT = jwtService.generateToken(user);
        String refreshT = jwtService.generateRefreshToken(user);

        tokenService.save(Token.builder().accessToken(accessT).refreshToken(refreshT).userName(user.getUsername()).build());

        return TokenResponse.builder()
                .accessToken(accessT)
                .refreshToken(refreshT)
                .userId(1L)
                .build();
    }

    @Override
    public TokenResponse refresh(HttpServletRequest request) {
        String token = request.getHeader("x-refresh-token");
        if(StringUtils.isBlank(token)){
            throw new InvalidDataException("Invalid refresh token must be not blank");
        }

        final String userName = jwtService.extractUsername(token, TokenType.REFRESH_TOKEN);
        System.out.println(userName);
        Optional<User> user = userRepository.findByUsername(userName);
        if(!jwtService.isTokenValid(token, TokenType.REFRESH_TOKEN , user.get())){
            throw new InvalidDataException("Invalid refresh token");
        }

        String accessToken = jwtService.generateToken(user.get());
        return TokenResponse.builder()
                .accessToken(accessToken)
                .refreshToken(token)
                .userId(user.get().getId())
                .build();

    }

    @Override
    public String logout(HttpServletRequest request) {
        String refresh = request.getHeader("x-refresh-token");
        if(StringUtils.isBlank(refresh)){
            throw new InvalidDataException("Invalid refresh token must be not blank");
        }
        final String username = jwtService.extractUsername(refresh, TokenType.REFRESH_TOKEN);
        Token currentToken = tokenService.findByUserName(username);
        tokenService.deleteToken(currentToken);
        return "Logout successfully";
    }


}
