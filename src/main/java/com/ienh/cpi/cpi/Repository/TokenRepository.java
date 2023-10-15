package com.ienh.cpi.cpi.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ienh.cpi.cpi.Models.UserToken;

public interface TokenRepository extends JpaRepository<UserToken, Integer> {
    // find by userId
    UserToken findByUser_id(int userId);

    // find by token
    UserToken findByToken(String token);
}
