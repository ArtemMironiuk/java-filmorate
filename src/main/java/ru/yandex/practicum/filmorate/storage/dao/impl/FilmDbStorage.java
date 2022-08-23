package ru.yandex.practicum.filmorate.storage.dao.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.dao.StorageFilm;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Slf4j
@Component
public class FilmDbStorage implements StorageFilm {

    private final JdbcTemplate jdbcTemplate;

    public FilmDbStorage (JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
    public List<Film> getFilms() {
        final String sqlQuery = "select FILMS.FILM_ID, FILMS.FILM_NAME, FILMS.DESCRIPTION, FILMS.RELEASE, " +
                "FILMS.DURATION,FILMS.RATE ,MPA.MPA_ID, MPA.MPA_NAME, GENRES.GENRE_ID, GENRES.GENRE_NAME " +
                "from FILMS " +
                "left join MPA on MPA.MPA_ID = FILMS.MPA_ID " +
                "left join FILMS_GENRES on FILMS.FILM_ID = FILMS_GENRES.FILM_ID " +
                "left join GENRES on GENRES.GENRE_ID = FILMS_GENRES.GENRE_ID ";
        List<Film> films = jdbcTemplate.query(sqlQuery, this::makeFilm);
        return films;
    }

    @Override
    public Film createFilm (Film film) {
        validate(film);
        String sqlFilms = "INSERT INTO FILMS (FILM_NAME, DESCRIPTION, RELEASE, DURATION, RATE, MPA_ID) VALUES (?,?,?,?,?,? )";
        KeyHolder keyHolder = new GeneratedKeyHolder();

            jdbcTemplate.update(con -> {
                PreparedStatement ps = con.prepareStatement(sqlFilms, new String[]{"FILM_ID"});
                ps.setString(1, film.getName());
                ps.setString(2, film.getDescription());
                ps.setDate(3, Date.valueOf(film.getReleaseDate()));
                ps.setInt(4, film.getDuration());
                ps.setInt(5, film.getRate());
                ps.setInt(6, film.getMpa().getId());
                return ps;
            }, keyHolder);
            film.setId(keyHolder.getKey().longValue());
        if (film.getGenres() != null) {
            addGenreByFilm(film);
        }
        return film;
    }

    @Override
    public Film updateFilm (Film film) {
            jdbcTemplate.update("UPDATE FILMS SET FILM_NAME = ?, DESCRIPTION = ?, RELEASE = ?, DURATION = ?, " +
                            "RATE = ?, MPA_ID = ? WHERE FILM_ID = ?",
                    film.getName(),
                    film.getDescription(),
                    film.getReleaseDate(),
                    film.getDuration(),
                    film.getRate(),
                    film.getMpa().getId(),
                    film.getId());
            log.info("Фильм с идентификатором {} обновлен.", film.getId());
            if (film.getGenres() != null && film.getGenres().size() >= 0) {
                List<Genre> genreSet = new LinkedList<>();
                if (film.getGenres().size() > 0) {
                    for (Genre genre : film.getGenres()) {
                        if (!genreSet.contains(genre)) {
                            genreSet.add(genre);
                        }
                    }
                }
                film.setGenres(genreSet);
                deleteGenreByFilm(film);
                addGenreByFilm(film);
            }
            return film;
    }

    @Override
    public Film getFilmId (Long id) {
            final String sqlQuery = "select FILMS.FILM_ID, FILMS.FILM_NAME, FILMS.DESCRIPTION, FILMS.RELEASE, " +
                    "FILMS.DURATION,FILMS.RATE ,MPA.MPA_ID, MPA.MPA_NAME, GENRES.GENRE_ID, GENRES.GENRE_NAME " +
                    "from FILMS " +
                    "left join MPA on MPA.MPA_ID = FILMS.MPA_ID " +
                    "left join FILMS_GENRES on FILMS.FILM_ID = FILMS_GENRES.FILM_ID " +
                    "left join GENRES on GENRES.GENRE_ID = FILMS_GENRES.GENRE_ID " +
                    "where FILMS.FILM_ID = ? GROUP BY GENRES.GENRE_NAME";
            final List<Film> film = jdbcTemplate.query(sqlQuery,this::makeFilm,id);
            if (film == null) {
                log.info("Фильм с идентификатором {} не найден.", id);
                return null;
            }
            log.info("Найден фильм: {}", film.get(0).getName());
            return film.get(0);
    }

    @Override
    public List<Film> getPopularFilms (Integer count) {
        String sqlQuery = "select FILMS.FILM_ID, FILMS.FILM_NAME, FILMS.DESCRIPTION, FILMS.RELEASE, " +
                "FILMS.DURATION,FILMS.RATE ,MPA.MPA_ID, MPA.MPA_NAME, GENRES.GENRE_ID, GENRES.GENRE_NAME " +
                "from FILMS " +
                "left join MPA on MPA.MPA_ID = FILMS.MPA_ID " +
                "left join FILMS_GENRES on FILMS.FILM_ID = FILMS_GENRES.FILM_ID " +
                "left join GENRES on GENRES.GENRE_ID = FILMS_GENRES.GENRE_ID order by FILMS.RATE desc limit  ?";
        List<Film> films = jdbcTemplate.query(sqlQuery,this::makeFilm,count);
        return films;
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

    private Film makeFilm (ResultSet rs, int rowNum) throws SQLException {
        List<Genre> genres = new ArrayList<>();
        if (findGenreByFilmId(rs.getLong("FILM_ID")) != null){
            List<Genre> listOfGenre = findGenreByFilmId(rs.getLong("FILM_ID"));
            genres.addAll(listOfGenre);
        }
        return Film.builder()
                .id(rs.getLong("FILM_ID"))
                .name(rs.getString("FILM_NAME"))
                .description(rs.getString("DESCRIPTION"))
                .releaseDate(rs.getDate("RELEASE").toLocalDate())
                .duration(rs.getInt("DURATION"))
                .rate(rs.getInt("RATE"))
                .mpa(Mpa.builder()
                        .id(rs.getInt("MPA.MPA_ID"))
                        .name(rs.getString("MPA.MPA_NAME"))
                        .build())
                .genres(genres)
                .build();
    }

    private Genre makeGenre (ResultSet rs, int rowNum) throws SQLException {
        return new Genre(rs.getInt("GENRE_ID"),
                rs.getString("GENRE_NAME"));
    }

    private void validate(Film film) {
        if (film.getName().isBlank()) {
            throw new ValidationException("Название фильма не может быть пустым.");
        } else if (film.getDescription().length() > 200) {
            throw new ValidationException("Описание к фильму не должно превышать 200 символов.");
        } else if (film.getReleaseDate().isBefore(LocalDate.of(1895,12,28))) {
            throw new ValidationException("Дата релиза должна быть позже 28.12.1895 года.");
        } else if (film.getDuration() < 0) {
            throw new ValidationException("Продолжительность фильма не может быть отрицательной.");
        }
    }
}
