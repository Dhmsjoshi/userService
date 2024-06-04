package dev.dharam.userservice.controllers;

import dev.dharam.userservice.dtos.CreateRoleRequestDto;
import dev.dharam.userservice.dtos.CreateRoleResponseDto;
import dev.dharam.userservice.entities.Role;
import dev.dharam.userservice.exceptions.UserDoesNotExistsException;
import dev.dharam.userservice.services.roleService.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/roles")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @PostMapping
    public ResponseEntity<CreateRoleResponseDto> createRole(@RequestBody CreateRoleRequestDto request){
        CreateRoleResponseDto role = roleService.createRole(request);
        return new ResponseEntity<>(role, HttpStatus.OK);
    }
    @PostMapping("/{id}")
    public ResponseEntity<String> setUserRoleAdmin(@PathVariable("id") Long userId) throws UserDoesNotExistsException {
        String res = roleService.setUserRoleAdmin( userId);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
