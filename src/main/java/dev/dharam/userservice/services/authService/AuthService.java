package dev.dharam.userservice.services.authService;

import dev.dharam.userservice.dtos.*;
import dev.dharam.userservice.entities.SessionStatus;
import dev.dharam.userservice.exceptions.UserAlreadyExistsException;
import dev.dharam.userservice.exceptions.UserDoesNotExistsException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

public interface AuthService {
    UserDto signUp(SignUpRequestDto requestDto) throws UserAlreadyExistsException;
    ResponseEntity<UserDto> login( LoginRequestDto request) throws UserDoesNotExistsException;

    Optional<UserDto> validate(ValidateTokenRequestDto request);
   ResponseEntity<Void> logout(LogoutRequestDto request);

}
