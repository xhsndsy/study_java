package com.example.springfirst.service;

import org.junit.jupiter.api.Order;
import org.springframework.stereotype.Component;

@Component
@Order(2)
public class PasswordValidator implements Validator{
    public void validate(String email, String password, String name) {
        if (!password.matches("^.{6,20}$")) {
            throw new IllegalArgumentException("invalid Password");
        }
    }
}
