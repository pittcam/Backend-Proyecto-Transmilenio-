package com.co.controller;

import com.co.dto.LoginRequestDTO;
import com.co.dto.LoginResponseDTO;
import com.co.service.AuthenticationService;
import com.co.service.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;


@RestController
@RequestMapping("/autenticacion")
public class AuthenticationController {

    @Autowired
    private AuthenticationService autenticacionService;



    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO body) {
        return ResponseEntity.ok(
                autenticacionService.login(body.getUsername(), body.getPassword())
        );
    }



    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@RequestHeader("Authorization") String token) {
        System.out.println("refresh" + token);
        return ResponseEntity.ok(
                autenticacionService.refresh(token)
        );
    }

}