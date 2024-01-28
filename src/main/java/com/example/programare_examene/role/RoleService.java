package com.example.programare_examene.role;


import com.example.programare_examene.role.model.Role;
import com.example.programare_examene.role.model.RoleType;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role getUserRole() {
        return roleRepository.findAll().stream().filter(r -> r.getType() == RoleType.USER).findFirst().get();
    }
}
