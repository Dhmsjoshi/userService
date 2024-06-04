package dev.dharam.userservice.controllers;

import dev.dharam.userservice.dtos.SetUserRolesRequestDto;
import dev.dharam.userservice.dtos.UserDto;
import dev.dharam.userservice.userService.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserDetails(@PathVariable("id") Long userId){
        UserDto userDto = userService.getUserDetails(userId);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }
    @GetMapping("/{id}/roles")
    public ResponseEntity<UserDto> setUserRoles(@PathVariable("id") Long userId, @RequestBody SetUserRolesRequestDto requestDto){
        UserDto userDto = userService.setUserRoles(userId, requestDto);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }
}
