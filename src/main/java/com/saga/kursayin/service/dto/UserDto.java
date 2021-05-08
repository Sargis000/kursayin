package com.saga.kursayin.service.dto;


import com.saga.kursayin.persistence.entity.UserDetailsEntity;
import com.saga.kursayin.persistence.entity.UserEntity;
import com.sun.istack.NotNull;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
public class UserDto {

    private Long id;

    @NotNull
    private String username;

    private String password;

    private String checkPassword;

    @Email(message = "Email should be valid")
    @NotEmpty(message = "Email should not be empty")
    private String email;

    private Boolean isActive;

    @Valid
    private UserDetailsDto userDetails;

    public static UserDto mapEntityToDto(UserEntity userEntity) {
        UserDto dto = new UserDto();
        dto.setId(userEntity.getId());
        dto.setUsername(userEntity.getUsername());
        dto.setPassword(userEntity.getPassword());
        dto.setIsActive(userEntity.getIsActive());
        dto.setEmail(userEntity.getEmail());
        UserDetailsEntity userDetailsEntity = userEntity.getUserDetailsEntity();
        if (userDetailsEntity != null) {
            dto.setUserDetails(UserDetailsDto.mapEntityToDto(userEntity.getUserDetailsEntity()));
        }
        return dto;
    }

    public static UserEntity mapDtoToEntity(UserDto userDto) {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(userDto.getId());
        userEntity.setPassword(userDto.getPassword());
        userEntity.setUsername(userDto.getUsername());
        userEntity.setIsActive(userDto.getIsActive());
        userEntity.setEmail(userDto.getEmail());
        return userEntity;
    }
}
