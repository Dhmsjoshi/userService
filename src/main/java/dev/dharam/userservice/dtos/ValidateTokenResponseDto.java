package dev.dharam.userservice.dtos;

import dev.dharam.userservice.entities.SessionStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class ValidateTokenResponseDto {
    private UserDto userDto;
    private SessionStatus sessionStatus;
}
