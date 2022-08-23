package ru.yandex.practicum.filmorate.storage.dao;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface StorageFilm {

    /**
     * Получение списка всех фильмов.
     * @return
     */
    List<Film> getFilms();

    /**
     * Добавить фильм в список.
     * @param film
     * @return
     */
    Film createFilm(Film film);

    /**
     * Обновить выбранный фильм.
     * @param film
     * @return
     */
    Film updateFilm(Film film);

    /**
     * Получение фильма по id.
     * @param id
     * @return
     */
    Film getFilmId(Long id);

    /**
     * Популярные фильмы
     * @param count
     * @return
     */
    List<Film> getPopularFilms(Integer count);
}
