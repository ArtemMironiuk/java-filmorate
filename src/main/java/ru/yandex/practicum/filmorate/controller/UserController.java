package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {

    private final HashMap<Integer, User> mapUsers = new HashMap<>();

    @GetMapping
    public List<User> usersAll(){
        return new ArrayList<>(mapUsers.values());
    }

    @PostMapping("/user")
    public User createUser(@RequestBody User user){
        if (!user.getEmail().isBlank() && user.getEmail().contains("@")) {
            if (!user.getLogin().isBlank() && !user.getLogin().contains(" ")) {
                if (!user.getBirthdate().isAfter(LocalDate.now())) {
                    if (user.getName().isEmpty()) {
                        user.setName(user.getLogin());
                        mapUsers.put(user.getId(), user);
                    } else {
                        mapUsers.put(user.getId(), user);
                    }
                } else {
                    throw new ValidationException("Дата рождения пользователя указана в будущем времени.");
                }
            } else {
                throw new ValidationException("Пожалуйста укажите логин или уберите пробел.");
            }
        } else {
            throw new ValidationException("Email пуст или отсутствует символ @.");
        }
        return user;
    }

    @PutMapping("/user")
    public User updateUser(@RequestBody User user){
        if (user.getId() != 0 && mapUsers.containsKey(user.getId())) {
            mapUsers.put(user.getId(), user);
        } else {
            throw new ValidationException("id пользователя не указан или не существует в списке.");
        }
        return user;
    }
}
