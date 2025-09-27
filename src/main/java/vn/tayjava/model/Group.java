package vn.tayjava.model;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tbl_group") 
public class Group extends AbstractEntity<Integer> {
    private String name;

    private String description;

    @OneToOne
    private Role roleId;

    @OneToMany(mappedBy = "groupId")
    Set<GroupHasUser> users = new HashSet<>();


}
