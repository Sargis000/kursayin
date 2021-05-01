package com.saga.kursayin.service.dto;

import com.saga.kursayin.persistence.entity.UserDetailsEntity;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class UserDetailsDto {

    private Long id;

    @NotEmpty(message = "FirstName should not be empty")
    @Size(min = 2,max = 30,message = "FirstName should be between 2 and 30 characters")
    private String  firstName;

    @NotEmpty(message = "LastName should not be empty")
    @Size(min = 2,max = 30,message = "LastName should be between 2 and 30 characters")
    private String lastName;

    @Pattern(regexp = "^(([+374]{4}|[0]{1}))?([1-9]{2})(\\d{6})$",message = "Phone should be valid")
    private String phoneNumber;

    @Min(value = 0,message = "Age should be greater than 0")
    private Integer age;

    public static UserDetailsDto mapEntityToDto(UserDetailsEntity userDetailsEntity){
        UserDetailsDto dto = new UserDetailsDto();
        dto.setId(userDetailsEntity.getId());
        dto.setFirstName(userDetailsEntity.getFirstName());
        dto.setLastName(userDetailsEntity.getLastName());
        dto.setAge(userDetailsEntity.getAge());
        dto.setPhoneNumber(userDetailsEntity.getPhoneNumber());
        return dto;
    }

    public static UserDetailsEntity mapDtoToEntity(UserDetailsDto userDetailsDto){
        UserDetailsEntity userDetailsEntity = new UserDetailsEntity();
        userDetailsEntity.setFirstName(userDetailsDto.getFirstName());
        userDetailsEntity.setLastName(userDetailsDto.getLastName());
        userDetailsEntity.setAge(userDetailsDto.getAge());
        userDetailsEntity.setPhoneNumber(userDetailsDto.getPhoneNumber());
        return userDetailsEntity;
    }
}
