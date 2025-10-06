package vn.tayjava.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import vn.tayjava.model.RedisToken;

@Repository
public interface RedisTokenRepository extends CrudRepository<RedisToken, String> {


    void deleteById(String username);

    RedisToken getById(String id);
}
