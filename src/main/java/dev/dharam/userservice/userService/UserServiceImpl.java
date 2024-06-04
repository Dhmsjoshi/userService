package dev.dharam.userservice.userService;

import dev.dharam.userservice.dtos.SetUserRolesRequestDto;
import dev.dharam.userservice.dtos.UserDto;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{
    @Override
    public UserDto getUserDetails(Long userId) {
        return null;
    }

    @Override
    public UserDto setUserRoles(Long userId, SetUserRolesRequestDto requestDto) {
        return null;
    }
}
