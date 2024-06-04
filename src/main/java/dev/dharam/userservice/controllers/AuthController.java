package dev.dharam.userservice.controllers;

import dev.dharam.userservice.dtos.*;
import dev.dharam.userservice.entities.SessionStatus;
import dev.dharam.userservice.exceptions.UserAlreadyExistsException;
import dev.dharam.userservice.exceptions.UserDoesNotExistsException;
import dev.dharam.userservice.services.authService.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity signUp(@RequestBody SignUpRequestDto request) throws UserAlreadyExistsException {
        UserDto userDto = authService.signUp(request);
        return new ResponseEntity(userDto, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginRequestDto request) throws UserDoesNotExistsException {
        ResponseEntity<UserDto> userDto = authService.login(request);
        return userDto;
    }
    @PostMapping("/logout")
    public ResponseEntity logout(@RequestBody LogoutRequestDto request){
        return null;
    }

    @PostMapping("/validate")
    public ResponseEntity<SessionStatus> validateToken(@RequestBody ValidateTokenRequestDto request){
        SessionStatus sessionStatus = authService.validate(request);
        return new ResponseEntity<>(sessionStatus, HttpStatus.OK);
    }


}
