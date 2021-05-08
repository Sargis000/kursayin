package com.saga.kursayin.service;

import com.saga.kursayin.common.exeptions.RoleNotFoundException;
import com.saga.kursayin.common.exeptions.UserNotFoundException;
import com.saga.kursayin.persistence.entity.RoleEntity;
import com.saga.kursayin.persistence.entity.UserDetailsEntity;
import com.saga.kursayin.persistence.entity.UserEntity;
import com.saga.kursayin.persistence.repository.RoleRepository;
import com.saga.kursayin.persistence.repository.UserRepository;
import com.saga.kursayin.service.dto.UserDetailsDto;
import com.saga.kursayin.service.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Map;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;
    private final EmailSender emailSender;

    @Autowired
    public UserService(UserRepository userRepository,
                       RoleRepository roleRepository, PasswordEncoder passwordEncoder,
                       UserDetailsService userDetailsService,
                       EmailSender emailSender) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
        this.emailSender = emailSender;
    }

    @Transactional
    public UserDto addUser(UserDto userDto) {
        UserEntity userEntity = UserDto.mapDtoToEntity(userDto);
        UserDetailsDto userDetailsDto = userDetailsService.addUserDetails(userDto.getUserDetails());
        UserDetailsEntity userDetailsEntity = UserDetailsDto.mapDtoToEntity(userDetailsDto);
        userDetailsEntity.setId(userDetailsDto.getId());
        userEntity.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userEntity.setUserDetailsEntity(userDetailsEntity);
        RoleEntity roleEntity = roleRepository.findById(1L).orElseThrow(()->new RoleNotFoundException(1L));
        userEntity.setRole(roleEntity);
        userEntity.setIsActive(false);
        UserEntity savedEntity = userRepository.save(userEntity);
        String html = "Verify Account\n" +"\n<a href='http://localhost:8085/users/"+userEntity.getId() +"/activate'>Click to activate</a>";
        String text="Verify your account";
        emailSender.sendEmail(userDto.getEmail(),html,text);
        return UserDto.mapEntityToDto(savedEntity);
    }

    public UserDto getUser(Long id) {
        UserEntity userEntity = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        return UserDto.mapEntityToDto(userEntity);
    }

    public UserDto updateUser(Long id, UserDto userDto) {
        UserEntity userEntity = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        userEntity.setUsername(userDto.getUsername());
        userEntity.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userEntity.setEmail(userDto.getEmail());
        UserEntity updatedUser = userRepository.save(userEntity);
        return UserDto.mapEntityToDto(updatedUser);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public void activateUser(Long id) {
        UserEntity userEntity = userRepository.findById(id).orElseThrow(()->new UserNotFoundException(id));
        userEntity.setIsActive(true);
        userRepository.save(userEntity);
    }

    public boolean checkemailinsystem(UserDto userDto){
        UserEntity userEntity=UserDto.mapDtoToEntity(userDto);
        userEntity= userRepository.findByEmail(userEntity.getEmail());

        if(userEntity==null){
            return false;
        }
        String html = "Click this link that change password\n" +"\n<a href='http://localhost:8085/users/"+userEntity.getId()+"/forgetpassword'>Link</a>";
        String text="Change Password";
        emailSender.sendEmail(userEntity.getEmail(),html,text);
        return true;
    }
    public void setpassword( UserDto userDto){
         UserEntity userEntity=UserDto.mapDtoToEntity(userDto);
         userRepository.savepassword(userEntity.getPassword(),userEntity.getId());
    }

}
