package ru.yandex.practicum.filmorate.storage.dao.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.dao.StorageUser;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@Component
public class UserDbStorage implements StorageUser {

    private final JdbcTemplate jdbcTemplate;

    public UserDbStorage (JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
    public List<User> getUsers() {
        final String sqlQuery = "select * from USERS";
        final List<User> users = jdbcTemplate.query(sqlQuery, UserDbStorage::makeUser);
        return users;
    }

    @Override
    public User createUser(User user) {
        validate(user);
        String sqlQuery = "INSERT INTO USERS (USER_NAME, LOGIN, EMAIL, BIRTHDAY)" + "VALUES(?,?,?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sqlQuery, new String[]{"USER_ID"});
            ps.setString(1, user.getName());
            ps.setString(2, user.getLogin());
            ps.setString(3, user.getEmail());
            ps.setDate(4, Date.valueOf(user.getBirthday()));
            return ps;
        }, keyHolder);
        user.setId(keyHolder.getKey().longValue());

        return user;
    }

    @Override
    public User updateUser(User user) {
        int i = jdbcTemplate.update("UPDATE USERS SET  USER_NAME = ?, LOGIN = ?, EMAIL = ?, BIRTHDAY = ? " +
                        " WHERE USER_ID = ?",
                user.getName(),
                user.getLogin(),
                user.getEmail(),
                user.getBirthday(),
                user.getId());
        if(i == 1){
            log.info("Пользователь с идентификатором {} обновлен.", user.getId());
            return user;
        }
        log.info("Пользователя с идентификатором {} нет.", user.getId());
        return null;
    }

    @Override
    public User getUserId(Long id) {
        final String sqlQuery = "select * from USERS where USER_ID = ?";
        final User user = jdbcTemplate.queryForObject(sqlQuery, UserDbStorage::makeUser,id);
        if(user.getId()!= id) {
            log.info("Пользователь с идентификатором {} не найден.", id);
            throw new ObjectNotFoundException("Пользователь с id = " + id + " не существует.");
        }
        log.info("Найден пользователь: {}", user);
        return user;
    }

    static User makeUser (ResultSet rs,int rowNum) throws SQLException {
        return new User(rs.getLong("USER_ID"),
                rs.getString("USER_NAME"),
                rs.getString("LOGIN"),
                rs.getString("EMAIL"),
                rs.getDate("BIRTHDAY").toLocalDate()
        );
    }

    private void validate(User user) {
        if (user.getEmail().isBlank() || !user.getEmail().contains("@")) {
            throw new ValidationException("Email пуст или отсутствует символ @.");
        } else if (user.getLogin().isBlank()) {
            throw new ValidationException("Пожалуйста укажите логин или уберите пробел.");
        } else if (user.getBirthday().isAfter(LocalDate.now())) {
            throw new ValidationException("Дата рождения пользователя указана в будущем времени.");
        } else if (user.getName().equals("")) {
            user.setName(user.getLogin());
        }
    }
}
