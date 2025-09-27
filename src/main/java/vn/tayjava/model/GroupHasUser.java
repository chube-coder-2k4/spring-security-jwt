package vn.tayjava.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class GroupHasUser extends AbstractEntity<Long> {
    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group groupId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User userId;
}
