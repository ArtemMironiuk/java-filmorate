package ru.yandex.practicum.filmorate.storage.dao;

public interface LikeDao {

    /**
     * Пользователь ставит лайк фильму
     * @param filmId
     * @param userId
     */
    void addLike(Long filmId, Long userId);

    /**
     * Удаление лайка
     * @param filmId
     * @param userId
     */
    void removeLike(Long filmId, Long userId);
}
