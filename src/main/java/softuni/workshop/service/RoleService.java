package softuni.workshop.service;

import softuni.workshop.domain.entities.Role;
import softuni.workshop.domain.models.service.RoleServiceModel;

import java.util.Set;

public interface RoleService {
    void seedRolesInDb();

    Set<RoleServiceModel> findAllRoles();

    RoleServiceModel findByAuthority(String role);

    Role getByAuthority(String role);
}
