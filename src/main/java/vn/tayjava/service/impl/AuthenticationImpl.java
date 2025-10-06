package vn.tayjava.service.impl;

import io.micrometer.common.util.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vn.tayjava.dto.request.ChangePasswordDTO;
import vn.tayjava.dto.request.SigninRequest;
import vn.tayjava.dto.request.TokenResponse;
import vn.tayjava.exception.InvalidDataException;
import vn.tayjava.exception.UserNotFoundException;
import vn.tayjava.model.RedisToken;
import vn.tayjava.model.Token;
import vn.tayjava.model.User;
import vn.tayjava.repository.UserRepository;
import vn.tayjava.service.*;
import vn.tayjava.util.TokenType;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final TokenService tokenService;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final RedisTokenService redisTokenService;

    @Override
    public TokenResponse authenticate(SigninRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        var user = userRepository.findByUsername(request.getUsername()).orElseThrow(() -> new UserNotFoundException("User not found"));
        String accessT = jwtService.generateToken(user);
        String refreshT = jwtService.generateRefreshToken(user);

//        tokenService.save(Token.builder().accessToken(accessT).refreshToken(refreshT).userName(user.getUsername()).build());
        redisTokenService.saveRedis(RedisToken.builder().accessToken(accessT).refreshToken(refreshT).id(user.getUsername()).resetToken(jwtService.generateResetToken(user)).build());

        return TokenResponse.builder()
                .accessToken(accessT)
                .refreshToken(refreshT)
                .userId(1L)
                .build();
    }

    @Override
    public TokenResponse refresh(HttpServletRequest request) {
        String token = request.getHeader("x-refresh-token");
        if (StringUtils.isBlank(token)) {
            throw new InvalidDataException("Invalid refresh token must be not blank");
        }

        final String userName = jwtService.extractUsername(token, TokenType.REFRESH_TOKEN);
        System.out.println(userName);
        Optional<User> user = userRepository.findByUsername(userName);
        if (!jwtService.isTokenValid(token, TokenType.REFRESH_TOKEN, user.get())) {
            throw new InvalidDataException("Invalid refresh token");
        }

        String accessToken = jwtService.generateToken(user.get());

        redisTokenService.saveRedis(RedisToken.builder().accessToken(accessToken).refreshToken(token).resetToken(jwtService.generateResetToken(user.get())).id(user.get().getUsername()).build());
        return TokenResponse.builder()
                .accessToken(accessToken)
                .refreshToken(token)
                .userId(user.get().getId())
                .build();

    }

    @Override
    public String logout(HttpServletRequest request) {
        String refresh = request.getHeader("x-refresh-token");
        if (StringUtils.isBlank(refresh)) {
            throw new InvalidDataException("Invalid refresh token must be not blank");
        }
        final String username = jwtService.extractUsername(refresh, TokenType.REFRESH_TOKEN);
        Token currentToken = tokenService.findByUserName(username);
        redisTokenService.deleteRedis(username);
        return "Logout successfully";
    }

    @Override
    public String forgotpassword(String email) {
        User us = userRepository.findByEmail(email).get();
        if (!us.isEnabled()) {
            throw new InvalidDataException("User is not active");
        }

        String token = jwtService.generateResetToken(us);
        // send email with reset password link

        redisTokenService.saveRedis(RedisToken.builder().resetToken(jwtService.generateResetToken(us)).accessToken(jwtService.generateToken(us)).refreshToken(jwtService.generateRefreshToken(us)).id(us.getUsername()).build());

        String link = String.format("curl --location 'http://localhost:80/auth/reset-password' \\\n" +
                "--header 'accept: */*' \\\n" +
                "--header 'Content-Type: application/json' \\\n" +
                "--data '%s'", token);
        log.info("--- confirm link --- = {}", link);
        return "sent";
    }

    @Override
    public String resetPassword(String token) {
        log.info("---------- Reset password request with token: {} ----------", token);
        final String userName = jwtService.extractUsername(token, TokenType.RESET_PASSWORD_TOKEN);
        var user = userRepository.findByUsername(userName);
        if (!jwtService.isTokenValid(token, TokenType.RESET_PASSWORD_TOKEN, user.get())) {
            throw new InvalidDataException("Invalid reset password token");
        }
        redisTokenService.getById(userName);
        return "Reset password successfully";
    }

    @Override
    public String changePassword(ChangePasswordDTO change) {
        User user = isValidUser(change.getSecretKey());

        if (!change.getNewPassword().equals(change.getConfirmPassword())) {
            throw new InvalidDataException("Confirm password is not match");
        }

        user.setPassword(passwordEncoder.encode(change.getNewPassword()));
        userService.saveUser(user);
        return "Change password successfully";
    }

    private User isValidUser(String secretKey) {
        final String userName = jwtService.extractUsername(secretKey, TokenType.RESET_PASSWORD_TOKEN);
        var user = userRepository.findByUsername(userName).orElseThrow(() -> new UserNotFoundException("User not found"));
        if (!jwtService.isTokenValid(secretKey, TokenType.RESET_PASSWORD_TOKEN, user)) {
            throw new InvalidDataException("Invalid reset password token");
        }
        return user;
    }
}
