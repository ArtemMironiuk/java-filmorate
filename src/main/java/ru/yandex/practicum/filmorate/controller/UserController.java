package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.StorageUser;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {

    private StorageUser storageUser;

    public UserController(StorageUser storageUser) {
        this.storageUser = storageUser;
    }

    @GetMapping
    public List<User> usersAll(){
        log.info("Получен запрос к эндпоинту: GET,http://localhost:8080/users");
        return storageUser.getUser();
    }

    @PostMapping
    public User createUser(@Valid @RequestBody User user){
        log.info("Получен запрос к эндпоинту: POST,http://localhost:8080/users");
        return storageUser.createUser(user);
    }

    @PutMapping
    public User updateUser(@Valid @RequestBody User user){
        log.info("Получен запрос к эндпоинту: PUT,http://localhost:8080/users");
        return storageUser.updateUser(user);
    }
}
