package com.example.instagram.repository;

import com.example.instagram.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,String> {
    public boolean existsByUsername(String username);
    public boolean existsByEmail(String email);
    Optional<User> findByUsername(String username);

}
