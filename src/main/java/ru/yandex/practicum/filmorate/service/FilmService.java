package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.dao.*;

import java.util.List;

@Service
public class FilmService {

    private LikeDao likeDao;
    private final StorageFilm storageFilm;

    @Autowired
    public FilmService(LikeDao likeDao, @Qualifier("userDbStorage") StorageUser storageUser,
                       @Qualifier("filmDbStorage") StorageFilm storageFilm) {
        this.likeDao = likeDao;
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
        if (film.getId() == null || film.getId() < 0){
            throw new ObjectNotFoundException("не указан id");
        }
        return storageFilm.updateFilm(film);
    }
    public void addLike(Long filmId, Long userId) {
        if (filmId <= 0 || userId <= 0) {
            throw new ObjectNotFoundException("id пользователя или id фильма указаны не верно. id должен быть больше 0");
        }
        likeDao.addLike(filmId,userId);
        Film film = storageFilm.getFilmId(filmId);
        if (film.getRate() == null) {
            film.setRate(1);
        }
        film.setRate(film.getRate() + 1);
        storageFilm.updateFilm(film);
    }

    public void deleteLike(Long filmId, Long userId) {
        if (filmId <= 0 || userId <= 0) {
            throw new ObjectNotFoundException("id пользователя или id фильма указаны не верно. id должен быть больше 0");
        }
        likeDao.removeLike(filmId,userId);
        Film film = storageFilm.getFilmId(filmId);
        film.setRate(film.getRate() - 1);
        storageFilm.updateFilm(film);
    }

    public List<Film> getFirstCountFilms(Integer count) {
        return storageFilm.getPopularFilms(count);
    }
}
