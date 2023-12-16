package com.ienh.cpi.cpi.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.ienh.cpi.cpi.DTO.TransactionDTO;
import com.ienh.cpi.cpi.DTO.UserDTO;
import com.ienh.cpi.cpi.Errors.SimpleError;
import com.ienh.cpi.cpi.Models.ErrorResponse;
import com.ienh.cpi.cpi.Models.User;
import com.ienh.cpi.cpi.Repository.TokenRepository;

public class TransactionsController {
    String errorMessage = "Transactions Controller Error";

    @Autowired
    private TokenRepository tokenRepository;

    // Controller Functions

    public void MakeError(String message, Exception error) throws Exception {
        throw new SimpleError(message, error);
    }

    // Routes

    @GetMapping("/")
    public ResponseEntity<?> index(@RequestBody @Validated TransactionDTO data) {
        try {
            User user = tokenRepository.findByToken(data.token()).getUser();

            return ResponseEntity.ok("Transactions Controller");
        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse(errorMessage, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
}
