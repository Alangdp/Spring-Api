package com.ienh.cpi.cpi.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ienh.cpi.cpi.Models.User;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findByEmail(String email);

    User findByCpf(String cpf);
}
