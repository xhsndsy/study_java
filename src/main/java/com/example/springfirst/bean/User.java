package com.example.springfirst.bean;

public class User {
    private String name;
    private Long id;
    private String email;

    private String password;

    
    public User(Long id, String email, String password, String name) {
        this.email = email;
        this.id = id;
        this.password = password;
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
