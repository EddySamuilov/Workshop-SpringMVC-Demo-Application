package softuni.workshop.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import softuni.workshop.domain.entities.Role;
import softuni.workshop.domain.entities.User;
import softuni.workshop.domain.models.binding.UserRegisterBindingModel;
import softuni.workshop.domain.models.service.UserServiceModel;
import softuni.workshop.repository.UserRepository;
import softuni.workshop.service.RoleService;
import softuni.workshop.service.UserService;

import javax.transaction.Transactional;
import java.util.LinkedHashSet;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleService roleService;
    private final ModelMapper modelMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleService roleService, ModelMapper modelMapper, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.modelMapper = modelMapper;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public UserServiceModel registerUser(UserRegisterBindingModel userRegisterBindingModel) {
        User user = this.modelMapper.map(userRegisterBindingModel, User.class);

        if (this.userRepository.count() == 0) {
            this.roleService.seedRolesInDb();
            user.setAuthorities(this.roleService.findAllRoles()
                    .stream()
                    .map(r -> this.modelMapper.map(r, Role.class))
                    .collect(Collectors.toSet()));
        } else {
            // user set role User
//            user.setAuthorities(new LinkedHashSet<>());
            user.getAuthorities().add(this.modelMapper.map(this.roleService.getByAuthority("USER"), Role.class));
        }

        user.setPassword(this.bCryptPasswordEncoder.encode(userRegisterBindingModel.getPassword()));
        return this.modelMapper.map(this.userRepository.save(user), UserServiceModel.class);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.userRepository.findByUsername(username);
    }
}
