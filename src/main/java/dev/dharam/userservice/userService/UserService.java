package dev.dharam.userservice.userService;

import dev.dharam.userservice.dtos.SetUserRolesRequestDto;
import dev.dharam.userservice.dtos.UserDto;

public interface UserService {
    UserDto getUserDetails(Long userId);
    UserDto setUserRoles(Long userId, SetUserRolesRequestDto requestDto);
}
