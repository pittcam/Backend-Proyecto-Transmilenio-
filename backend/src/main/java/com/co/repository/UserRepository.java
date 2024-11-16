package com.co.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.co.model.User;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    // Since email is unique, we'll find users by email
    Optional<User> findByUsername(String username);
    boolean existsByCorreo(String correo);
    boolean existsByUsername(String username);
    boolean existsByCedula(String cedula);
}

