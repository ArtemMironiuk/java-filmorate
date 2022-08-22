package ru.yandex.practicum.filmorate.storage.dao;

public interface FriendsDao {

    /**
     * Добавление в друзья.
     * @param userId
     * @param friendId
     */
    void addFriend(Long userId, Long friendId);

    /**
     * Удаление из друзей.
     * @param userId
     * @param friendId
     */
    void deleteFriend(Long userId, Long friendId);
}
