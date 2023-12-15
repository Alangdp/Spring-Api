package com.ienh.cpi.cpi.Controllers;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ienh.cpi.cpi.DTO.UserDTO;
import com.ienh.cpi.cpi.Errors.SimpleError;
import com.ienh.cpi.cpi.Models.ErrorResponse;
import com.ienh.cpi.cpi.Models.User;
import com.ienh.cpi.cpi.Models.UserToken;
import com.ienh.cpi.cpi.Repository.TokenRepository;
import com.ienh.cpi.cpi.Repository.UserRepository;
import com.ienh.cpi.cpi.Utils.Extras;
import com.ienh.cpi.cpi.Utils.PasswordUtils;

import ch.qos.logback.core.subst.Token;

import java.util.HashMap;

@RestController()
@RequestMapping("/api/users")
public class UserController {
    String errorMessage = "User Controller Error";

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenRepository tokenRepository;

    // CONTROLLER FUNCTIONS

    public void MakeError(String message, Exception error) throws Exception {
        throw new SimpleError(message, error);
    }

    // ROUTES

    @GetMapping("/")
    public ResponseEntity<?> index() {
        try {
            Map<String, ArrayList<User>> response = new HashMap<>();
            ArrayList<User> users = (ArrayList<User>) userRepository.findAll();
            response.put("users", users);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse(errorMessage, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @PostMapping("/")
    public ResponseEntity<?> store(@RequestBody @Validated UserDTO data) {
        try {
            User user = new User(data);
            user = userRepository.save(user);
            Map<String, User> response = new HashMap<>();
            response.put("user", user);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse(errorMessage, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> delete(@PathVariable("id") int id) {
        try {
            User user = userRepository.findById(id).orElseThrow();
            userRepository.delete(user);

            return ResponseEntity.ok(user);
        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse(errorMessage, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @PutMapping("/")
    ResponseEntity<?> update(@RequestBody @Validated UserDTO data) {
        try {
            User user = tokenRepository.findByToken(data.token()).getUser();
            if (user == null)
                throw new Exception("User not found");
            user.setName(Extras.getValueOrDefault(data.name(), user.getName()));
            user.setEmail(Extras.getValueOrDefault(data.email(), user.getEmail()));
            user.setCpf(Extras.getValueOrDefault(data.cpf(), user.getCpf()));
            user.setPhone(Extras.getValueOrDefault(data.phone(), user.getPhone()));
            user.setPassword(Extras.getValueOrDefault(data.password(), user.getPassword()));
            user.validOnSave();
            user = userRepository.save(user);

            Map<String, User> response = new HashMap<>();
            response.put("user", user);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse(errorMessage, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @PostMapping("/login")
    ResponseEntity<?> login(@RequestBody @Validated UserDTO data) {
        User user = null;
        try {
            user = userRepository.findByEmail(data.email());
            if (user == null)
                throw new Exception("User not found");

            if (!PasswordUtils.compare(data.password(), user.getPassword()))
                throw new Exception("Incorrect password");

            UserToken token = new UserToken(Extras.generateToken(), "user", user);
            token = tokenRepository.save(token);
            return ResponseEntity.ok(token);
        } catch (Exception e) {
            if (e instanceof DataIntegrityViolationException) {
                UserToken token = tokenRepository.findByUser_id(user.getId());
                if (token != null) {
                    tokenRepository.delete(token);
                }
                UserToken newToken = new UserToken(Extras.generateToken(), "user", user);
                newToken = tokenRepository.save(newToken);
                return ResponseEntity.ok(newToken);
            }

            ErrorResponse error = new ErrorResponse(errorMessage, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
}
