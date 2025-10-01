package vn.tayjava.service;

import org.springframework.stereotype.Service;
import vn.tayjava.model.Token;
import vn.tayjava.repository.TokenRepository;

import java.util.Optional;

@Service
public record TokenService(TokenRepository tokenRepository) {

    public Long save(Token token) {
        Optional<Token> tk = tokenRepository.findByUserName(token.getUserName());
        if(tk.isEmpty()) {
            tokenRepository.save(token);
            return token.getId();
        } else {
            Token existingToken = tk.get();
            existingToken.setAccessToken(token.getAccessToken());
            existingToken.setRefreshToken(token.getRefreshToken());
            tokenRepository.save(existingToken);
            return existingToken.getId();
        }
    }

    public Token findByUserName(String userName) {
        return tokenRepository.findByUserName(userName).get();
    }

    public String deleteToken(Token token) {
        tokenRepository.delete(token);
        return "Delete token successfully";
    }
}
