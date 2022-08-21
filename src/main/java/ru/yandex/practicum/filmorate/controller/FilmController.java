package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/films")
@Slf4j
public class FilmController {
    private final FilmService filmService;
    @Autowired
    public FilmController( FilmService filmService) {
        this.filmService = filmService;
    }

    @GetMapping
    public List<Film> filmsAll() {
        log.info("Получен запрос к эндпоинту: GET,http://localhost:8080/films");
        return filmService.getFilms();
    }

    @GetMapping("/{id}")
    public Film film(@PathVariable Long id) {
        if (id == null || id < 0) {
            throw new ObjectNotFoundException("не указан id");
        }
        log.info("Получен запрос к эндпоинту: GET,http://localhost:8080/films/{id}");
        return filmService.getFilmId(id);
    }

    @PostMapping
    public Film createFilm(@Valid @RequestBody Film film) {
        if (film.getMpa() == null) {
            throw new ValidationException("проверьте все поля");
        }
        log.info("Получен запрос к эндпоинту: POST,http://localhost:8080/films");
        return filmService.createFilm(film);
    }

    @PutMapping
    public Film updateFilm(@Valid @RequestBody Film film) {
        log.info("Получен запрос к эндпоинту: PUT,http://localhost:8080/films");
        return filmService.updateFilm(film);
    }

    @PutMapping("/{filmId}/like/{userId}")
    public void addLike(@PathVariable Long filmId, @PathVariable Long userId) {
        filmService.addLike(filmId, userId);
    }

    @DeleteMapping("/{filmId}/like/{userId}")
    public void deleteLike(@PathVariable Long filmId, @PathVariable Long userId) {
        filmService.deleteLike(filmId, userId);
    }

    @GetMapping("/popular") //?count={count}
    public List<Film> getFirstCountFilms(@RequestParam (defaultValue = "10") Integer count) {
        return filmService.getFirstCountFilms(count);
    }
}
