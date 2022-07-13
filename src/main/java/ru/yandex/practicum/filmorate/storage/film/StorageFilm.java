package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface StorageFilm {

    List<Film> getFilms();

    Film createFilm(Film film);

    Film updateFilm(Film film);
}
