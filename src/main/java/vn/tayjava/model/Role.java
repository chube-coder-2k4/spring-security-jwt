package vn.tayjava.model;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Role extends AbstractEntity<Integer> {
    private String name;

    private String description;

    @OneToMany(mappedBy = "roleId")
    private Set<RoleHasPermission> permission = new HashSet<>();

    @OneToMany(mappedBy = "roleId")
    private Set<UserHasRole> roles = new HashSet<>();
}
