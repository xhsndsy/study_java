package com.example.springfirst.service;

import org.junit.jupiter.api.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
public class Emailvalidator implements Validator {
    public void validate(String email, String password, String name) {
        if (!email.matches("^[a-z0-9]+\\@[a-z0-9]+\\.[a-z]{2,10}$")) {
            throw new IllegalArgumentException("invalid email: " + email);
        }
    }
}
