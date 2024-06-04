package dev.dharam.userservice.services.roleService;

import dev.dharam.userservice.dtos.CreateRoleRequestDto;
import dev.dharam.userservice.entities.Role;

public interface RoleService {
    Role createRole(CreateRoleRequestDto request);
}
