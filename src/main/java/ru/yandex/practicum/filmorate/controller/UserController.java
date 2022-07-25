package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.user.StorageUser;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {

    private final StorageUser storageUser;
    private final UserService userService;

    public UserController(StorageUser storageUser, UserService userService) {
        this.storageUser = storageUser;
        this.userService = userService;
    }

    @GetMapping
    public List<User> usersAll(){
        log.info("Получен запрос к эндпоинту: GET,http://localhost:8080/users");
        return storageUser.getUsers();
    }

    @GetMapping("/{id}")
    public User user(@PathVariable Long id){
        log.info("Получен запрос к эндпоинту: GET,http://localhost:8080/users/{id}");
        return storageUser.getUserId(id);
    }

    @PostMapping
    public User createUser(@Valid @RequestBody User user) {
        log.info("Получен запрос к эндпоинту: POST,http://localhost:8080/users");
        return storageUser.createUser(user);
    }

    @PutMapping
    public User updateUser(@Valid @RequestBody User user) {
        log.info("Получен запрос к эндпоинту: PUT,http://localhost:8080/users");
        return storageUser.updateUser(user);
    }

    @PutMapping("/{userId}/friends/{friendId}")
    public void addFriend(@PathVariable Long userId, @PathVariable Long friendId) {
        userService.addFriend(userId,friendId);
    }

    @DeleteMapping("/{userId}/friends/{friendId}")
    public void deleteFriend(@PathVariable Long userId, @PathVariable Long friendId) {
        userService.deleteFriend(userId,friendId);
    }

    @GetMapping("/{id}/friends")
    public void getFriends(@PathVariable Long id) {
        userService.getFriends(id);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public void getMutualFriends(@PathVariable Long id, @PathVariable Long otherId) {
        userService.getMutualFriends(id,otherId);
    }
}
