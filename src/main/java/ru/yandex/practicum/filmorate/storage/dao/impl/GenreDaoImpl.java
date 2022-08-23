package ru.yandex.practicum.filmorate.storage.dao.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.dao.GenreDao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Slf4j
@Component
public class GenreDaoImpl implements GenreDao {

    private final JdbcTemplate jdbcTemplate;

    public GenreDaoImpl (JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public Genre findById(Integer id) {
        final String sqlQuery ="select * from GENRES where GENRE_ID = ?";
        final Genre genre = jdbcTemplate.queryForObject(sqlQuery, this::makeGenre,id);
        return genre;
    }

    @Override
    public List<Genre> findAll() {
        final String sqlQuery = "select * from GENRES";
        final List<Genre> genres = jdbcTemplate.query(sqlQuery,this::makeGenre);
        return genres;
    }

    private Genre makeGenre (ResultSet rs, int rowNum) throws SQLException {
        return new Genre(rs.getInt("GENRE_ID"),
                rs.getString("GENRE_NAME"));
    }
}
