package com.saga.kursayin.controller;

import com.saga.kursayin.persistence.entity.UserEntity;
import com.saga.kursayin.service.dto.UserDto;
import com.saga.kursayin.service.UserService;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
@Controller
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String showRegisterForm() {
        return "register";
    }

    @PostMapping("/register")
    public String addUser(@Valid @ModelAttribute("userDto")  UserDto userDto,
                          BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return showRegisterForm();
        }
        try {
                userService.addUser(userDto);
        } catch (Exception e) {
            return "redirect:/users/register?existed";
        }
        return "redirect:/users/register?success";
    }


    @GetMapping("{id}/forgetpassword")
    public String ForgetPassword(Model model, @PathVariable Long id){
        model.addAttribute("userpas", userService.getUser(id));
        return "ForgetPasswordPage";
    }

    @GetMapping("/mailchange")
    public String mailChengPassword(){
        return "MailPageChangePassword";
    }

    @GetMapping("/checkemail")
    public String checkEmail(@ModelAttribute UserDto userDto){
       if(userService.checkemailinsystem(userDto)) {
           return "redirect:/users/mailchange?success";
       } else {
           return "redirect:/users/mailchange?email_not_found";
       }
    }


    @PostMapping("/changepassword/{id}")
    public String changPassword(@ModelAttribute UserDto userDto){
        userService.setpassword(userDto);
        return "redirect:/login";
    }


    @ModelAttribute("userDto")
    public UserDto userDto() {
        return new UserDto();
    }


    @GetMapping("{id}/activate")
    public String activateUser(@PathVariable Long id){
        userService.activateUser(id);
        return "redirect:/login";
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable Long id) {
        UserDto userDto = userService.getUser(id);
        return ResponseEntity.ok(userDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long id, @RequestBody UserDto userDto) {
        UserDto dto = userService.updateUser(id, userDto);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }
}
