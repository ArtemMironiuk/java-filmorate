package ru.yandex.practicum.filmorate.storage.dao.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.storage.dao.LikeDao;

@Slf4j
@Component
public class LikeDaoImpl implements LikeDao {
    private JdbcTemplate jdbcTemplate;

    public LikeDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void addLike(Long filmId, Long userId) {
        String sqlQuery = "insert into LIKES(FILM_ID, USER_ID) values ( ?, ? )";
        jdbcTemplate.update(sqlQuery,filmId,userId);
        log.info("Поставлен лайк фильму с id = {},от пользователя с id = {}",filmId,userId);
    }

    @Override
    public void removeLike(Long filmId, Long userId) {
        String sqlRemove = "delete from LIKES where FILM_ID = ? AND USER_ID = ?";
        jdbcTemplate.update(sqlRemove,filmId,userId);
        log.info("Пользователь с id = {} убрал лайку у фильма с id = {}",userId,filmId);
    }


}
