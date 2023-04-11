package com.example.springfirst.service;

import com.example.springfirst.bean.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.boot.autoconfigure.cassandra.CassandraProperties;
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;


@Component
public class UserService {
    @Autowired
    private MailService mailService;

    private List<User> users = new ArrayList<>(List.of( // users:
            new User(1L, "bob@example.com", "password", "Bob"), // bob
            new User(2L, "alice@example.com", "password", "Alice"), // alice
            new User(3L, "tom@example.com", "password", "Tom"))); // tom


    public User login(String email, String password) {
        for (User user : users) {
            if (user.getEmail().equalsIgnoreCase(email) && user.getPassword().equals(password)) {
                mailService.sendLoginMail(user);
                return user;
            }
        }
        throw new RuntimeException("login failed.");
    }

    public User getUser(long id) {
        return this.users.stream().filter(user -> user.getId() == id).findFirst().orElseThrow();
    }


    @MetricTime("register")
    public User register(String email, String password, String name) {
        users.forEach((user) -> {
            if (user.getEmail().equalsIgnoreCase(email)) {
                throw new RuntimeException("email exist.");
            }
        });
        User user = new User((Long) (users.stream().mapToLong(u -> u.getId()).max().getAsLong() + 1), email, password, name);
        users.add(user);
        mailService.sendRegistrationMail(user);
        return user;
    }
}
