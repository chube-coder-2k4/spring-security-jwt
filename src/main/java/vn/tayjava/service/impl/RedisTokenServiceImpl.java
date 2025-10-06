package vn.tayjava.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import vn.tayjava.model.RedisToken;
import vn.tayjava.repository.RedisTokenRepository;
import vn.tayjava.service.RedisTokenService;

@Service
@AllArgsConstructor
public class RedisTokenServiceImpl implements RedisTokenService {
    private final RedisTokenRepository redisTokenRepository;

    public String saveRedis(RedisToken token){
        RedisToken newToken = redisTokenRepository.save(token);
        return newToken.getId();
    }

    public void deleteRedis(String id){
        redisTokenRepository.deleteById(id);
    }

    @Override
    public RedisToken getById(String userName) {
        return redisTokenRepository.getById(userName);
    }

}
