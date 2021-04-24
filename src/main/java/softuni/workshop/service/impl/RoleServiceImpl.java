package softuni.workshop.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.workshop.domain.entities.Role;
import softuni.workshop.domain.models.service.RoleServiceModel;
import softuni.workshop.repository.RoleRepository;
import softuni.workshop.service.RoleService;

import javax.transaction.Transactional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository, ModelMapper modelMapper) {
        this.roleRepository = roleRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void seedRolesInDb() {
        Role admin = new Role("ADMIN");
        Role user = new Role("USER");

        this.roleRepository.save(admin);
        this.roleRepository.save(user);
    }

    @Override
    public Set<RoleServiceModel> findAllRoles() {
        return this.roleRepository.findAll()
                .stream()
                .map(r -> this.modelMapper.map(r, RoleServiceModel.class))
                .collect(Collectors.toSet());
    }

    @Override
    public RoleServiceModel findByAuthority(String role) {
        return this.modelMapper.map(this.roleRepository.findByAuthority(role), RoleServiceModel.class);
    }

    @Override
    public Role getByAuthority(String role){
        return this.roleRepository.findByAuthority(role);
    }
}
