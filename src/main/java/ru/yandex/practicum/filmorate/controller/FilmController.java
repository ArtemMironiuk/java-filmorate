package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/films")
@Slf4j
public class FilmController {

    private final HashMap<Integer, Film> mapFilms = new HashMap<>();
    private int id = 1;

    @GetMapping
    public List<Film> filmsAll(){
        log.info("Получен запрос к эндпоинту: GET,http://localhost:8080/films");
        return new ArrayList<>(mapFilms.values());
    }

    @PostMapping
    public Film createFilm(@Valid @RequestBody Film film){
        log.info("Получен запрос к эндпоинту: POST,http://localhost:8080/films");
        validate(film);
        film.setId(id++);
        mapFilms.put(film.getId(), film);
        return film;
    }

    @PutMapping
    public Film updateFilm(@RequestBody Film film){
        log.info("Получен запрос к эндпоинту: PUT,http://localhost:8080/films");
        validate(film);
        if (film.getId() <= 0) {
            throw new ValidationException("id отрицательный");
        }
        if (film.getId() != 0 && mapFilms.containsKey(film.getId())) {
            mapFilms.put(film.getId(), film);
        } else {
            throw new ValidationException("id фильма равен нулю или такого фильма нет в списке.");
        }
        return film;
    }

    public void validate(Film film) {
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
