package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.exception.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.service.GenreService;

import java.util.List;

@RestController
@RequestMapping("/genres")
@Slf4j
public class GenreController {

    private GenreService genreService;

    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @GetMapping("/{id}")
    public Genre findById(@PathVariable Integer id) {
        if (id == null || id < 0) {
            throw new ObjectNotFoundException("не указан id");
        }
        log.info("Получен запрос к эндпоинту: GET,http://localhost:8080/genres/{id}");
        return genreService.findById(id);
    }

    @GetMapping
    public List<Genre> findAll() {
        log.info("Получен запрос к эндпоинту: GET,http://localhost:8080/genres");
        return genreService.findAll();
    }
}
