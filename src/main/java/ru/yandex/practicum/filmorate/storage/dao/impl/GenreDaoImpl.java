package ru.yandex.practicum.filmorate.storage.dao.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.dao.GenreDao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
        final String sqlQuerty ="select * from GENRES where GENRE_ID = ?";
        final Genre genre = jdbcTemplate.queryForObject(sqlQuerty, this::makeGenre,id);
        return genre;
    }

    @Override
    public List<Genre> findAll() {
        final String sqlQuery = "select * from GENRES";
        final List<Genre> genres = jdbcTemplate.query(sqlQuery,this::makeGenre);
        return genres;
    }

    public void addGenreByFilm(Film film) {
        final String sqlQuery ="insert into FILMS_GENRES(FILM_ID,GENRE_ID) VALUES(?,?)";
            for (Genre genre:film.getGenres()) {
                jdbcTemplate.update(sqlQuery, film.getId(), genre.getId());
            }
    }
    public List<Genre> findGenreByFilmId(Long filmId) {
        final String sqlQuery = "select GENRE_ID from FILMS_GENRES where FILM_ID = ?";
        List<Integer> integers = jdbcTemplate.queryForList(sqlQuery,Integer.class,filmId);
        List<Genre> genres = new ArrayList<>();
        if (integers != null) {
            final String sqlGenre = "select * from GENRES where GENRE_ID = ?";
            for (Integer integer : integers) {
                final Genre genre = jdbcTemplate.queryForObject(sqlGenre, this::makeGenre, integer);
                genres.add(genre);
            }
            return genres;
        }
        return null;
    }

    public void deleteGenreByFilm(Film film) {
            final String sqlQuery = "DELETE FROM FILMS_GENRES WHERE FILM_ID = ?";
            jdbcTemplate.update(sqlQuery, film.getId());
    }

    private Genre makeGenre (ResultSet rs, int rowNum) throws SQLException {
        return new Genre(rs.getInt("GENRE_ID"),
                rs.getString("GENRE_NAME"));
    }
}
