package vn.tayjava.service;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import vn.tayjava.model.Role;
import vn.tayjava.repository.RoleRepository;

import java.util.List;

@Service
public record RoleService(RoleRepository repository) {

    @PostConstruct
    public List<Role> findAll() {
        List<Role> roles = repository.findAll();
        return roles;
    }

}
