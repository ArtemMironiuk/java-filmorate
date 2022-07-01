package java.ru.yandex.practicum.filmorate.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.ru.yandex.practicum.filmorate.model.User;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
public class UserController {

    private final HashMap<Integer, User> mapUsers = new HashMap<>();

    @GetMapping
    public List<User> usersAll(){
        return new ArrayList<>(mapUsers.values());
    }
}
