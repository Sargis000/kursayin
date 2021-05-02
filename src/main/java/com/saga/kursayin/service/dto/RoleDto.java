package com.saga.kursayin.service.dto;

import com.saga.kursayin.persistence.entity.RoleEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleDto {

    private Long id;

    private String name;

    public static RoleDto mapEntityToDto(RoleEntity entity) {
        if (entity == null) {
            return null;
        }
        RoleDto dto = new RoleDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        return dto;
    }

    public static RoleEntity mapDtoToEntity(RoleDto roleDto) {
        if (roleDto == null) {
            return null;
        }
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setName(roleDto.getName());
        return roleEntity;
    }

}
