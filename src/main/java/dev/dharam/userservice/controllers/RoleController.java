package dev.dharam.userservice.controllers;

import dev.dharam.userservice.dtos.CreateRoleRequestDto;
import dev.dharam.userservice.entities.Role;
import dev.dharam.userservice.services.roleService.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/roles")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @PostMapping
    public ResponseEntity<Role> createRole(@RequestBody CreateRoleRequestDto request){
        Role role = roleService.createRole(request);
        return new ResponseEntity<>(role, HttpStatus.OK);
    }
}
