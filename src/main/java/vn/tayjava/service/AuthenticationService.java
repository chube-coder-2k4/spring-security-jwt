package vn.tayjava.service;

import vn.tayjava.dto.request.SigninRequest;
import vn.tayjava.dto.request.TokenResponse;

public interface AuthenticationService {
    TokenResponse authenticate(SigninRequest request);
}
