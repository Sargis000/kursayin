package com.saga.kursayin.service;

import com.saga.kursayin.common.exeptions.UserDetailsNotFoundException;
import com.saga.kursayin.persistence.entity.UserDetailsEntity;
import com.saga.kursayin.persistence.repository.UserDetailsRepository;
import com.saga.kursayin.service.dto.UserDetailsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;

@Service
public class UserDetailsService {

    private final UserDetailsRepository userDetailsRepository;

    @Autowired
    public UserDetailsService(UserDetailsRepository userDetailsRepository) {
        this.userDetailsRepository = userDetailsRepository;
    }

    public UserDetailsDto addUserDetails(@Valid UserDetailsDto userDetailsDto) {
        UserDetailsEntity userDetailsEntity = UserDetailsDto.mapDtoToEntity(userDetailsDto);
        UserDetailsEntity savedEntity = userDetailsRepository.save(userDetailsEntity);
        return UserDetailsDto.mapEntityToDto(savedEntity);
    }

    public UserDetailsDto updateUserDetails(Long id, UserDetailsDto userDetailsDto) {
        UserDetailsEntity userDetailsEntity = userDetailsRepository.findById(id).
                orElseThrow(() -> new UserDetailsNotFoundException(id));
        userDetailsEntity.setFirstName(userDetailsDto.getFirstName());
        userDetailsEntity.setLastName(userDetailsDto.getLastName());
        userDetailsEntity.setAge(userDetailsDto.getAge());
        userDetailsEntity.setPhoneNumber(userDetailsDto.getPhoneNumber());
        UserDetailsEntity updatedEntity = userDetailsRepository.save(userDetailsEntity);
        return UserDetailsDto.mapEntityToDto(updatedEntity);
    }
}
