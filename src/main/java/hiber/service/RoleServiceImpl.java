package hiber.service;


import hiber.model.Role;
import hiber.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService{

    private final RoleRepository roleRepository;

    @Override
    public Set<Role> getRoles() {
        return new HashSet<>(roleRepository.findAll());
    }

    @Override
    public Set<Role> findRoleByRole(String[] stringsRoles) {
        Set<Role> setRole = new HashSet<>();
        for(String role : stringsRoles) {
            setRole.add(roleRepository.findRoleByName(role));
        }
        return setRole;
    }
}
