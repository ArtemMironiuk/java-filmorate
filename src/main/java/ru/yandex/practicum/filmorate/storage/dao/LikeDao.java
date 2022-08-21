package ru.yandex.practicum.filmorate.storage.dao;

public interface LikeDao {
    void addLike(Long filmId, Long userId);

    void removeLike(Long filmId, Long userId);
}
