package vn.tayjava.model;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.*;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Permission extends AbstractEntity<Integer> {

    private String name;

    private String description;

    @OneToMany(mappedBy = "permissionId")
    private java.util.Set<RoleHasPermission> permissions = new java.util.HashSet<>();
}
