package ru.yandex.practicum.filmorate.dao;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.dao.impl.FilmDbStorage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class FilmDaoTest {
    private final FilmDbStorage storageFilm;

    @Test
    public void testCreateFilm() {
        List<Genre> genres = new ArrayList<>();
        genres.add(new Genre(2,null));
        Film film = storageFilm.createFilm(new Film(1L,"New","dsfsd", LocalDate.now(),50,
                1,new Mpa(1,null),genres));
        assertThat(film)
                .isNotNull();
    }

    @Test
    public void testGetFilms() {
        List<Film> filmList = storageFilm.getFilms();
        assertThat(filmList)
                .isNotNull();
    }

    @Test
    public void testFindFilmById() {
        List<Genre> genres = new ArrayList<>();
        genres.add(new Genre(2,null));
        Film film = storageFilm.createFilm(new Film(1L,"New","dsfsd", LocalDate.now(),50,
                1,new Mpa(1,null),genres));
        Film film1 = storageFilm.getFilmId(1L);
        assertThat(film1)
                .isNotNull();
    }
}
