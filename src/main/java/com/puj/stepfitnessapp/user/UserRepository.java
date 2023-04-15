package com.puj.stepfitnessapp.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    @Query("SELECT u FROM User u WHERE u.enterToken = ?1")
    Optional<User> findUserByToken(String token);

    @Transactional
    @Modifying
    @Query("UPDATE User SET enterToken = ?1")
    void addEnterToken(String token);
}
