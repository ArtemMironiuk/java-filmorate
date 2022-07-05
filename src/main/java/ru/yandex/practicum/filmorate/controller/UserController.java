package ru.yandex.practicum.filmorate.controller;

import org.springframework.web.bind.annotation.*;

import ru.yandex.practicum.filmorate.model.User;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final HashMap<Integer, User> mapUsers = new HashMap<>();

    @GetMapping
    public List<User> usersAll(){
        return new ArrayList<>(mapUsers.values());
    }

    @PostMapping("/user")
    public User createUser(@RequestBody User user){

        return user;
    }

    @PutMapping("/user")
    public User updateUser(@RequestBody User user){

        return user;
    }
}
