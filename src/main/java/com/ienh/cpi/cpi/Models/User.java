package com.ienh.cpi.cpi.Models;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.*;

import com.ienh.cpi.cpi.DTO.UserDTO;
import com.ienh.cpi.cpi.Errors.SimpleError;
import com.ienh.cpi.cpi.Utils.Extras;
import com.ienh.cpi.cpi.Utils.PasswordUtils;

@Entity
@Table(name = "users")
public class User {

    // Variables

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = true, unique = true)
    private String cpf;

    @Column(nullable = true)
    private String phone;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    // Functions

    public void validOnSave() throws SimpleError {
        if (this.name == null || this.name.isEmpty()) {
            throw new SimpleError("Invalid Name");
        }

        this.name = Extras.capitalize(this.name);

        if (this.email == null || this.email.isEmpty()) {
            throw new SimpleError("Invalid Email");
        }

        this.email = this.email.toLowerCase();

        if (!PasswordUtils.validPassword(this.password)) {
            throw new SimpleError("Invalid Password");
        }
        this.password = PasswordUtils.encrypt(this.password);

        if (this.cpf == null || this.cpf.isEmpty() || this.cpf.length() < 11) {
            throw new SimpleError("Invalid CPF");
        }

        if (this.phone == null || this.phone.isEmpty() || this.phone.length() < 10) {
            throw new SimpleError("Invalid Phone");
        }
    }

    // Getters and Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    // Constructors

    public User() {
    }

    public User(int id) {
        this.id = id;
    }

    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public User(String name, String email, String password, String cpf, String phone) throws SimpleError {
        this.name = name;
        this.email = email;
        this.password = password;
        this.cpf = cpf;
        this.phone = phone;
        this.createdAt = LocalDateTime.now();
        validOnSave();
    }

    public User(UserDTO data) throws SimpleError {
        this.name = data.name();
        this.email = data.email();
        this.password = data.password();
        this.cpf = data.cpf();
        this.phone = data.phone();
        this.createdAt = LocalDateTime.now();
        validOnSave();
    }

}
