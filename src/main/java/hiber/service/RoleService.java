package hiber.service;

import hiber.model.Role;
import hiber.model.User;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface RoleService {

    Set<Role> getRoles();


    Set<Role> findRoleByRole(String[] strings);
}
