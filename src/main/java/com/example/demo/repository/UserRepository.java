package com.example.demo.repository;

import com.example.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

// Repository interface for User entity, providing user-specific database operations.
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * Checks if a user exists with the given email.
     * @param email The user's email.
     * @return True if exists, false otherwise.
     */
    boolean existsByEmail(String email);

    /**
     * Checks if a user exists with the given username.
     * @param username The user's username.
     * @return True if exists, false otherwise.
     */
    boolean existsByUsername(String username);

    /**
     * Finds a user by their email.
     * @param email The user's email.
     * @return An Optional containing the user if found.
     */
    Optional<User> findByEmail(String email);
}
