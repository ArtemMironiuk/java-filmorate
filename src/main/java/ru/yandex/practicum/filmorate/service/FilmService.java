package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.film.StorageFilmsImpl;
import ru.yandex.practicum.filmorate.storage.user.StorageUsersImpl;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

@Service
public class FilmService {
    private final StorageUsersImpl storageUser;
    private final StorageFilmsImpl storageFilm;

    public FilmService(StorageUsersImpl storageUser, StorageFilmsImpl storageFilm) {
        this.storageUser = storageUser;
        this.storageFilm = storageFilm;
    }

    public void addLike(Long filmId, Long userId) {
        User user = storageUser.getUserId(userId);
        Film film = storageFilm.getFilmId(filmId);
        film.getUserIds().add(user.getId());
//        //TODO check userId and friendId
//        storageFilm.addLike(film,user);
    }

    public void deleteLike(Long filmId, Long userId) {
        User user = storageUser.getUserId(userId);
        Film film = storageFilm.getFilmId(filmId);
        film.getUserIds().remove(user.getId());
//        //TODO check userId and friendId
//        storageFilm.deleteLike(film,user);
    }

    public List<Film> getFirstCountFilms(Integer count) {
        Set<Film> films = new TreeSet<>((p0, p1) -> {
            if((p0.getUserIds().size()!= 0) &&(p1.getUserIds().size() != 0)) {
                return p0.getUserIds().size().compareTo(p1.getUserIds().size());
            } else if (p0.getUserIds().size() == 0) {
                return 1;
            } else if (p1.getUserIds().size() == 0) {
                return -1;
            }
            return 1;
        });

        return films;
    }
}
