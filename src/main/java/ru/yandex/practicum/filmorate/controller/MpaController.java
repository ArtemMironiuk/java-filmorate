package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.exception.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.service.MpaService;

import java.util.List;

@RestController
@RequestMapping("/mpa")
@Slf4j
public class MpaController {

    private MpaService mpaService;

    public MpaController(MpaService mpaService) {
        this.mpaService = mpaService;
    }

    @GetMapping("/{id}")
    public Mpa findById(@PathVariable Integer id){
        if (id == null || id < 0){
            throw new ObjectNotFoundException("не указан id");
        }
        log.info("Получен запрос к эндпоинту: GET,http://localhost:8080/mpa/{id}");
        return mpaService.findById(id);
    }

    @GetMapping
    public List<Mpa> findAll(){
        log.info("Получен запрос к эндпоинту: GET,http://localhost:8080/mpa");
        return mpaService.findAll();
    }
}
