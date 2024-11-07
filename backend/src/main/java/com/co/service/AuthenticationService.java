package com.co.service;

import com.co.dto.JwtAuthenticationResponse;
import com.co.dto.LoginDTO;
import com.co.model.Role;
import com.co.dto.UserRegistrationDTO;
import com.co.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.co.model.User;


@Service
public class AuthenticationService {

    private Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private AuthenticationManager authenticationManager;

    public JwtAuthenticationResponse signup(UserRegistrationDTO request) {
        User user = new User(
                request.getNombre(),
                request.getCedula(),
                request.getCorreo(),
                passwordEncoder.encode(request.getContrasena()),
                Role.USER);

        userRepository.save(user);
        String jwt = jwtService.generateToken(user);
        return new JwtAuthenticationResponse(jwt, user.getCorreo(), user.getRole());
    }

    public JwtAuthenticationResponse login(LoginDTO request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getCorreo(), request.getContrasena()));
        User user = userRepository.findByCorreo(request.getCorreo())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password."));
        String jwt = jwtService.generateToken(user);
        return new JwtAuthenticationResponse(jwt, user.getCorreo(), user.getRole());
    }

}

