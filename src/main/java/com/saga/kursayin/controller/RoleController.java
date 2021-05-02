package com.saga.kursayin.controller;

import com.saga.kursayin.service.RoleService;
import com.saga.kursayin.service.dto.RoleDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * @author Gurgen Poghosyan
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/roles")
@PreAuthorize("hasAuthority('ADMIN')")
public class RoleController {

    private final RoleService roleService;

    @PostMapping()
    public ResponseEntity<RoleDto> createRole(@RequestBody RoleDto roleDto) {
        if (roleDto.getName() == null) {
            throw new NullPointerException("Role name is required");
        }
        RoleDto dto = roleService.create(roleDto);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoleDto> getRole(@PathVariable Long id) {
        RoleDto dto = roleService.get(id);
        return ResponseEntity.ok(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoleDto> updateRole(@PathVariable Long id,
                                                @RequestBody RoleDto roleDto) {
        RoleDto dto = roleService.update(roleDto, id);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{id}")
    public void deleteRole(@PathVariable Long id) {
        roleService.delete(id);
    }
}
