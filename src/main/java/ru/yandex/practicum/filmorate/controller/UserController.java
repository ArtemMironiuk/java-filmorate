package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {
    private final HashMap<Integer, User> mapUsers = new HashMap<>();
    private Integer id = 1;

    @GetMapping
    public List<User> usersAll(){
        log.info("Получен запрос к эндпоинту: GET,http://localhost:8080/users");
        return new ArrayList<>(mapUsers.values());
    }

    @PostMapping
    public User createUser(@Valid @RequestBody User user){
        log.info("Получен запрос к эндпоинту: POST,http://localhost:8080/users");
        validate(user);
        user.setId(id++);
        mapUsers.put(user.getId(), user);
        return user;
    }

    @PutMapping
    public User updateUser(@Valid @RequestBody User user){
        log.info("Получен запрос к эндпоинту: PUT,http://localhost:8080/users");
        validate(user);
        if (user.getId() <= 0) {
            throw new ValidationException("id отрицательный");
        }
        if (user.getId() != null && mapUsers.containsKey(user.getId())) {
            mapUsers.put(user.getId(), user);
        } else {
            throw new ValidationException("id пользователя не указан или не существует в списке.");
        }
        return user;
    }

    public void validate(User user) {
        if (user.getEmail().isBlank() || !user.getEmail().contains("@")) {
            throw new ValidationException("Email пуст или отсутствует символ @.");
        } else if (user.getLogin().isBlank()) {
            throw new ValidationException("Пожалуйста укажите логин или уберите пробел.");
        } else if (user.getBirthday().isAfter(LocalDate.now())) {
            throw new ValidationException("Дата рождения пользователя указана в будущем времени.");
        } else if (user.getName().equals("")) {
            user.setName(user.getLogin());
        }
    }
}
