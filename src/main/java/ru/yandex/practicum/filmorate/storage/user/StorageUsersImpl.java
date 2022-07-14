package ru.yandex.practicum.filmorate.storage.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Slf4j
@Component
public class StorageUsersImpl implements StorageUser{

    private final HashMap<Long, User> mapUsers = new HashMap<>();

    private Long id = 1L;

    public List<User> getUsers(){
        return new ArrayList<>(mapUsers.values());
    }

    public User createUser(User user){
        validate(user);
        user.setId(id++);
        mapUsers.put(user.getId(), user);
        log.debug("User c id = " + user.getId() + " создан.");
        return user;
    }

    public User updateUser(User user){
        if (user.getId() <= 0) {
            throw new ValidationException("id отрицательный");
        }
        if (user.getId() != null && mapUsers.containsKey(user.getId())) {
            mapUsers.put(user.getId(), user);
            log.debug("User c id = " + user.getId() + " обновлен.");
        } else {
            throw new ValidationException("id пользователя не указан или не существует в списке.");
        }
        return user;
    }

    private void validate(User user) {
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

    public void addFriend(User user, User friend) {
        user.getFriendIds().add(friend.getId());
        friend.getFriendIds().add(user.getId());
    }

    public void deleteFriend(User user, User friend) {
        user.getFriendIds().remove(friend.getId());
        friend.getFriendIds().remove(user.getId());
    }
}
