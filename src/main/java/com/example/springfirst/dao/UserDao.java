package com.example.springfirst.dao;

import com.example.springfirst.bean.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

@Component
public class UserDao {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public User getUserById(long id) {
        // 注意传入的是ConnectionCallback:
        return jdbcTemplate.execute((Connection conn) -> {
            // 可以直接使用conn实例，不要释放它，回调结束后JdbcTemplate自动释放:
            // 在内部手动创建的PreparedStatement、ResultSet必须用try(...)释放:
            try (var ps = conn.prepareStatement("SELECT * FROM users WHERE id = ?")) {
                ps.setObject(1, id);
                try (var rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return new User( // new User object:
                                rs.getLong("id"), // id
                                rs.getString("email"), // email
                                rs.getString("password"), // password
                                rs.getString("name")); // name
                    }
                    throw new RuntimeException("user not found by id.");
                }
            }
        });
    }

    public User getUserByName(String name) {
        return jdbcTemplate.execute("select * from users where name = ?", (PreparedStatement ps) -> {
            ps.setObject(1, name);
            try (var rs = ps.executeQuery()){
                if (rs.next()) {
                    return new User(
                            rs.getLong("id"),
                            rs.getNString("email"),
                            rs.getString("password"),
                            rs.getString("name")
                    );
                }
                throw new RuntimeException("user not found by id");
            }
        });
    }

    public User getUserByEmail(String email) {
        return jdbcTemplate.queryForObject("select * from users where email = ?",
                (ResultSet rs, int rowNum)->{
                    return new User(
                            rs.getLong("id"),
                            rs.getString("email"),
                            rs.getString("password"),
                            rs.getString("name"));
        }, email);
    }

    public void updateUser(User user) {
        if (1 != jdbcTemplate.update("update users set name = ? where id = ?", user.getName(), user.getId())) {
            throw new RuntimeException("User not found ");
            }
        }

    public User register(String email, String password, String name) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        if (1 != jdbcTemplate.update(con -> {
          var ps = con.prepareStatement("insert into users(emial, password, name) values",
                  Statement.RETURN_GENERATED_KEYS);
          ps.setObject(1, email);
          ps.setObject(2, password);
          ps.setObject(3, name);
          return ps;
        }, keyHolder)
        ){
            throw new RuntimeException("Insert failed. ");
        }
        return new User(keyHolder.getKey().longValue(), email, password, name);
    }
 }
