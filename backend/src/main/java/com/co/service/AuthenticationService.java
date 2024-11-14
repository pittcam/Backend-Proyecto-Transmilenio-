package com.co.service;

import java.util.Optional;

import com.co.dto.LoginResponseDTO;
import com.co.model.User;
import com.co.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;



@Service
public class AuthenticationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    public LoginResponseDTO login(String username, String password) {
        Optional<User> usuario = userRepository.findByNombre(username);

        if (usuario == null) {
            throw new UsernameNotFoundException("Usuario no encontrado");
        }

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

        String token = jwtService.generateToken(usuario.get());
        return new LoginResponseDTO(token, username, usuario.get().getRol().getTipoRol());
    }

    public LoginResponseDTO refresh(String token) {
        token = token.substring(7);
        String username = jwtService.extractUserName(token);
        Optional<User> usuario = userRepository.findByNombre(username);

        if (usuario == null) {
            throw new UsernameNotFoundException("Usuario no encontrado");
        }

        token = jwtService.generateToken(usuario.get());

        return new LoginResponseDTO(token, username, usuario.get().getRol().getTipoRol());
    }

}