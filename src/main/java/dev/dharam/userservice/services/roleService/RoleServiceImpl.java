package dev.dharam.userservice.services.roleService;

import dev.dharam.userservice.dtos.CreateRoleRequestDto;
import dev.dharam.userservice.dtos.CreateRoleResponseDto;
import dev.dharam.userservice.entities.Role;
import dev.dharam.userservice.entities.User;
import dev.dharam.userservice.exceptions.UserDoesNotExistsException;
import dev.dharam.userservice.repositories.RoleRepository;
import dev.dharam.userservice.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
public class RoleServiceImpl implements RoleService{

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;
    @Override
    public CreateRoleResponseDto createRole(CreateRoleRequestDto request) {
        Role role = new Role();
        role.setName(request.getName());
       Role savedRole =roleRepository.save(role);

       return CreateRoleResponseDto.from(role);
    }

    @Override
    public String setUserRoleAdmin( Long userId) throws UserDoesNotExistsException {
        Optional<User> userOptional = userRepository.findById(userId);
        if(userOptional.isEmpty()){
            throw new UserDoesNotExistsException("User does not exist with id: "+userId);
        }

        Role role = roleRepository.findByName("admin").orElseThrow(
                ()->new RuntimeException("Unknown error")
        );

        User user = userOptional.get();
        Set<Role> roles = user.getRoles();
        roles.add(role);
        user.setRoles(roles);
        user = userRepository.save(user);
        return "Success";

    }

}
