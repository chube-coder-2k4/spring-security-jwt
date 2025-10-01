package vn.tayjava.model;

import jakarta.persistence.Entity;
import lombok.*;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Token extends AbstractEntity<Long> {
    private String userName;

    private String accessToken;

    private String refreshToken;


}
