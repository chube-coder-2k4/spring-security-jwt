package vn.tayjava.service;

import vn.tayjava.model.RedisToken;

public interface RedisTokenService {

    String saveRedis(RedisToken token);

    void deleteRedis(String id);

    RedisToken getById(String userName);
}
