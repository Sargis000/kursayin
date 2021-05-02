package com.saga.kursayin.security;

import com.saga.kursayin.service.dto.UserDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@CrossOrigin
@RequestMapping("/login")
public class JwtAuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final JwtUserDetailService userDetailsService;

    public JwtAuthenticationController(AuthenticationManager authenticationManager,
                                       JwtTokenUtil jwtTokenUtil,
                                       JwtUserDetailService userDetailsService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userDetailsService = userDetailsService;
    }

    @ModelAttribute("jwtRequest")
    public JwtRequest jwtRequest() {
        return new JwtRequest();
    }

    @GetMapping()
    public String showLoginForm() {
        return "login";
    }

    @PostMapping()
    public String createAuthenticationToken(@ModelAttribute("jwtRequest") JwtRequest authenticationRequest) {

        try{
            authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
            final UserDetails userDetails = userDetailsService
                    .loadUserByUsername(authenticationRequest.getUsername());

            final String token = jwtTokenUtil.generateToken(userDetails);

        } catch(Exception e){
            return "redirect:/login?badCredentials";
        }
        return "accountPage";
    }

    private void authenticate(String username, String password) throws DisabledException, BadCredentialsException{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }
}