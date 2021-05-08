package com.saga.kursayin.controller;

import com.saga.kursayin.service.UserDetailsService;
import com.saga.kursayin.service.dto.UserDetailsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user_details")
public class UserDetailsController {

    private final UserDetailsService userDetailsService;

    @Autowired
    public UserDetailsController(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<UserDetailsDto> updateUserDetails(@PathVariable Long id, @RequestBody UserDetailsDto userDetailsDto) {
        UserDetailsDto dto = userDetailsService.updateUserDetails(id, userDetailsDto);
        return ResponseEntity.ok(dto);
    }
}
