package ru.yandex.practicum.filmorate.controller;

import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/films")
public class FilmController {

    private final HashMap<Integer, Film> mapFilms = new HashMap<>();

    @GetMapping
    public List<Film> filmsAll(){
        return new ArrayList<>(mapFilms.values());
    }

    @PostMapping("/film")
    public Film createFilm(@RequestBody Film film){
        if (!film.getName().isBlank()) {
            if (film.getDescription().length() <= 200) {
                if (film.getReleaseDate().isBefore(LocalDate.of(1895,12,28))){
                    if (!film.getDuration().isNegative()) {
                        mapFilms.put(film.getId(), film);
                    } else {
                        throw new ValidationException("Продолжительность фильма не может быть отрицательной.");
                    }
                } else {
                    throw new ValidationException("Дата релиза должна быть позже 28.12.1895 года.");
                }
            } else {
                throw new ValidationException("Описание к фильму не должно превышать 200 символов.");
            }
        } else {
            throw new ValidationException("Название фильма не может быть пустым.");
        }
        return film;
    }

    @PutMapping("/film")
    public Film updateFilm(@RequestBody Film film){

        return film;
    }
}
