package vn.tayjava.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import vn.tayjava.model.RedisToken;

@Repository
public interface RedisTokenRepository extends CrudRepository<RedisToken, Long> {
    // deleteById
    void deleteById(String id);
    //getById
    RedisToken getById(String id);
}
