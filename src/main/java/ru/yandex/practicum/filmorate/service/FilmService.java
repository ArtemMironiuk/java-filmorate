package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.film.StorageFilm;
import ru.yandex.practicum.filmorate.storage.user.StorageUser;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FilmService {
    private final StorageUser storageUser;
    private final StorageFilm storageFilm;

    @Autowired
    public FilmService(StorageUser storageUser, StorageFilm storageFilm) {
        this.storageUser = storageUser;
        this.storageFilm = storageFilm;
    }

    public List<Film> getFilms() {
        return storageFilm.getFilms();
    }

    public Film getFilmId(Long id){
        return storageFilm.getFilmId(id);
    }

    public Film createFilm(Film film) {
        return storageFilm.createFilm(film);
    }

    public Film updateFilm(Film film){
        return storageFilm.updateFilm(film);
    }
    /**
     * Пользователь ставит лайк фильму
     * @param filmId
     * @param userId
     */
    public void addLike(Long filmId, Long userId) {
        if (filmId <= 0 || userId <= 0) {
            throw new ObjectNotFoundException("id пользователя или id фильма указаны не верно. id должен быть больше 0");
        }
        User user = storageUser.getUserId(userId);
        Film film = storageFilm.getFilmId(filmId);
        film.getUserIds().add(user.getId());
    }

    /**
     * Удаление лайка
     * @param filmId
     * @param userId
     */
    public void deleteLike(Long filmId, Long userId) {
        if (filmId <= 0 || userId <= 0) {
            throw new ObjectNotFoundException("id пользователя или id фильма указаны не верно. id должен быть больше 0");
        }
        User user = storageUser.getUserId(userId);
        Film film = storageFilm.getFilmId(filmId);
        film.getUserIds().remove(user.getId());
    }

    /**
     * Получение фильмов по рейтингу, если count = 0, то первые 10 фильмов
     * @param count
     * @return
     */
    public List<Film> getFirstCountFilms(Integer count) {
        List<Film> films = storageFilm.getFilms();
        films = films.stream()
                .sorted((p0, p1) -> {
            if((p0.getUserIds().size() != 0) && (p1.getUserIds().size() != 0)) {
                return p1.getUserIds().size() - (p0.getUserIds().size());
            } else if (p0.getUserIds().size() == 0) {
                return 1;
            } else if (p1.getUserIds().size() == 0) {
                return -1;
            }
            return 1;
        })
                .limit(count)
                .collect(Collectors.toList());

        return films;
    }
}
