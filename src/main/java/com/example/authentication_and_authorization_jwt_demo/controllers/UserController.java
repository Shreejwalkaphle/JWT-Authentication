package com.example.authentication_and_authorization_jwt_demo.controllers;

import com.example.authentication_and_authorization_jwt_demo.ModelClass.JwtModels;
import com.example.authentication_and_authorization_jwt_demo.ModelClass.JwtResponse;
import com.example.authentication_and_authorization_jwt_demo.Services.UserService;
import com.example.authentication_and_authorization_jwt_demo.config.MyWebSecurityConfig;
import com.example.authentication_and_authorization_jwt_demo.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
//    we need a service in controller
    @Autowired
    UserService userService;

//    now we need a authentication manager to authenticate the username and password
    @Autowired
    AuthenticationManager authenticationManager;

//    aba util chaincha for calling generate token method
    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @PostMapping("/authenticate")
    public JwtResponse authentication(@RequestBody JwtModels jwtModels){
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(jwtModels.getUsername(),jwtModels.getPassword()));
//yaha UsernamePasswordAuthenticationToken le naya token generate garcha for the provided username and password

        }
        catch (Exception e){}
        final UserDetails userDetails = userService.loadUserByUsername(jwtModels.getUsername());
        final String token = jwtTokenUtil.generateToken(userDetails);
        return new JwtResponse(token);
    }

    @GetMapping("/")
    public String home(){
        return "Hello from home";
    }

}
