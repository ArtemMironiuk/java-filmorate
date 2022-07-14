package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.film.StorageFilmsImpl;
import ru.yandex.practicum.filmorate.storage.user.StorageUsersImpl;

@Service
public class FilmService {

    @Autowired
    StorageUsersImpl storageUser;
    @Autowired
    StorageFilmsImpl storageFilm;

    public void addLike(int userId, int filmId) {
//        User user = storageUser.getUser(userId);
//        Film film = storageFilm.getFilm(filmId);
//        //TODO check userId and friendId
//        storageFilm.addLike(film,user);
    }

    public void deleteLike(int userId, int filmId) {
//        User user = storageUser.getUser(userId);
//        Film film = storageFilm.getFilm(filmId);
//        //TODO check userId and friendId
//        storageFilm.deleteLike(film,user);
    }
}
