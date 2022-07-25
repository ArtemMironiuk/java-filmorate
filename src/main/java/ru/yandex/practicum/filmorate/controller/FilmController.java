package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.storage.film.StorageFilm;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/films")
@Slf4j
public class FilmController {
    private final StorageFilm storageFilm;
    private final FilmService filmService;
    public FilmController(StorageFilm storageFilm, FilmService filmService) {
        this.storageFilm = storageFilm;
        this.filmService = filmService;
    }

    @GetMapping
    public List<Film> filmsAll(){
        log.info("Получен запрос к эндпоинту: GET,http://localhost:8080/films");
        return storageFilm.getFilms();
    }

    @GetMapping("/{id}")
    public Film film(@PathVariable Long id){
        log.info("Получен запрос к эндпоинту: GET,http://localhost:8080/films/{id}");
        return storageFilm.getFilmId(id);
    }

    @PostMapping
    public Film createFilm(@Valid @RequestBody Film film){
        log.info("Получен запрос к эндпоинту: POST,http://localhost:8080/films");
        return storageFilm.createFilm(film);
    }

    @PutMapping
    public Film updateFilm(@Valid @RequestBody Film film){
        log.info("Получен запрос к эндпоинту: PUT,http://localhost:8080/films");
        return storageFilm.updateFilm(film);
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
    public void getFirstCountFilms(@RequestParam (defaultValue = "10") Integer count) {
        filmService.getFirstCountFilms(count);
    }
}
