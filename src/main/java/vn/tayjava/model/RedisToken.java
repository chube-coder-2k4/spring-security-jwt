package vn.tayjava.model;

import jakarta.persistence.Entity;
import lombok.*;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@RedisHash("RedisToken")
public class RedisToken implements Serializable {
    private String id;
    private String accessToken;
    private String refreshToken;
    private String resetToken;

}
