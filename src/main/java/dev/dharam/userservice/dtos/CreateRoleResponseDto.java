package dev.dharam.userservice.dtos;

import dev.dharam.userservice.entities.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateRoleResponseDto {
    private String name;

    public static CreateRoleResponseDto from(Role role){
        CreateRoleResponseDto responseDto = new CreateRoleResponseDto();
        responseDto.setName(role.getName());
        return responseDto;
    }
}
