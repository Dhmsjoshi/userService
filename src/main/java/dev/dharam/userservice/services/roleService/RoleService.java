package dev.dharam.userservice.services.roleService;

import dev.dharam.userservice.dtos.CreateRoleRequestDto;
import dev.dharam.userservice.dtos.CreateRoleResponseDto;
import dev.dharam.userservice.entities.Role;
import dev.dharam.userservice.exceptions.UserDoesNotExistsException;

import java.util.UUID;

public interface RoleService {
    CreateRoleResponseDto createRole(CreateRoleRequestDto request);
    String setUserRoleAdmin(Long userId) throws UserDoesNotExistsException;
}
