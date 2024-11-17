package com.co.controller;

import com.co.dto.UserDTO;
import com.co.model.Rol;
import com.co.model.User;
import com.co.repository.RoleRepository;
import com.co.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/usuarios")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping
    public ResponseEntity<?> registrarUsuario(@RequestBody UserDTO userDTO) {
        System.out.println("Datos recibidos: " + userDTO);
        // Validar si el correo ya está en uso
        if (userRepository.existsByCorreo(userDTO.getCorreo())) {
            return ResponseEntity.badRequest().body("El correo ya está en uso");
        }

        // Validar si el username ya está en uso
        if (userRepository.existsByUsername(userDTO.getUsername())) {
            return ResponseEntity.badRequest().body("El username ya está en uso");
        }

        // Validar si la cédula ya está en uso
        if (userRepository.existsByCedula(userDTO.getCedula())) {
            return ResponseEntity.badRequest().body("La cédula ya está en uso");
        }

        // Verificar si el rol USER existe
        Rol rolUser = roleRepository.findByTipoRol("USER")
                .orElseThrow(() -> new RuntimeException("Rol USER no encontrado"));

        // Crear y guardar el usuario
        User usuario = new User();
        usuario.setNombre(userDTO.getNombre());
        usuario.setCedula(userDTO.getCedula());
        usuario.setCorreo(userDTO.getCorreo());
        usuario.setUsername(userDTO.getUsername());
        usuario.setContrasena(passwordEncoder.encode(userDTO.getContrasena())); // Encriptar la contraseña
        usuario.setRol(rolUser);

        userRepository.save(usuario);
        System.out.println("Usuario registrado: " + usuario);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(Map.of("message", "El usuario fue registrado exitosamente"));

    }

}
