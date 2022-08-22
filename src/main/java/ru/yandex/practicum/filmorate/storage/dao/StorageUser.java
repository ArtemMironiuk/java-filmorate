package ru.yandex.practicum.filmorate.storage.dao;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface StorageUser {

    /**
     * Получение списка всех пользователей.
     * @return
     */
    List<User> getUsers();

    /**
     * Добавить пользователя в список.
     * @param user
     * @return
     */
    User createUser(User user);

    /**
     * Обновить пользователя.
     * @param user
     * @return
     */
    User updateUser(User user);

    /**
     * Получение пользователя по id.
     * @param id
     * @return
     */
    User getUserId(Long id);

    /**
     * Получение списка пользователей, являющихся его друзьями
     * @param id
     * @return
     */
    List<User> getFriends(Long id);

    /**
     * Получение списка друзей, общих с другим пользователем.
     * @param id
     * @param otherId
     * @return
     */
    List<User> getMutualFriends(Long id, Long otherId);
}
