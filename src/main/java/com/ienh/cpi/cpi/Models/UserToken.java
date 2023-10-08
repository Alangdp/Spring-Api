package com.ienh.cpi.cpi.Models;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.ienh.cpi.cpi.Repository.TokenRepository;

import jakarta.persistence.*;

@Entity
@Table(name = "tokens")
public class UserToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, length = 10)
    private String token;

    @Column(nullable = false, length = 5)
    private String type;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public UserToken() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setType(String type) {
        this.type = type;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public UserToken(String token, String type, User user) {
        this.token = token;
        this.type = type;
        this.user = user;
    }

    public String getToken() {
        return this.token;
    }

    public String getType() {
        return this.type;
    }
}
