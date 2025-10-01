package vn.tayjava.service;

import jakarta.servlet.http.HttpServletRequest;
import vn.tayjava.dto.request.SigninRequest;
import vn.tayjava.dto.request.TokenResponse;

public interface AuthenticationService {
    TokenResponse authenticate(SigninRequest request);


    TokenResponse refresh(HttpServletRequest request);
}
