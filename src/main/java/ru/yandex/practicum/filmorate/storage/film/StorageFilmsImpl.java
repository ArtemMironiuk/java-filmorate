package ru.yandex.practicum.filmorate.storage.film;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Slf4j
@Component
public class StorageFilmsImpl implements StorageFilm {

    private final HashMap<Long, Film> mapFilms = new HashMap<>();

    private Long id = 1L;

    public List<Film> getFilms() {
        return new ArrayList<>(mapFilms.values());
    }

    public Film getFilmId(Long id){
        if (mapFilms.containsKey(id)) {
            return mapFilms.get(id);
        } else {
            throw new ObjectNotFoundException("Фильм с id = " + id + " не существует.");
        }
    }

    public Film createFilm(Film film) {
        validate(film);
        film.setId(id++);
        mapFilms.put(film.getId(), film);
        log.debug("Film c id = " + film.getId() + " создан.");
        return film;
    }

    public Film updateFilm(Film film){
        if (film.getId() <= 0) {
            throw new ObjectNotFoundException("id отрицательный");
        }
        if (film.getId() != 0 && mapFilms.containsKey(film.getId())) {
            mapFilms.put(film.getId(), film);
            log.debug("Film c id = " + film.getId() + " обновлен.");
        } else {
            throw new ObjectNotFoundException("id фильма равен нулю или такого фильма нет в списке.");
        }
        return film;
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
