package com.example.instagram.repository;

import com.example.instagram.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,String> {
    public boolean existsByUsername(String username);
    public boolean existsByEmail(String email);

}
