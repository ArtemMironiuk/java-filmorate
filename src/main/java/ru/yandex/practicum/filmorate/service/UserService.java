package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.dao.FriendsDao;
import ru.yandex.practicum.filmorate.storage.dao.StorageUser;

import java.util.List;

@Service
public class UserService {

    private final StorageUser storageUser;
    private FriendsDao friendsDao;

    @Autowired
    public UserService(@Qualifier("userDbStorage") StorageUser storageUser, FriendsDao friendsDao) {
        this.storageUser = storageUser;
        this.friendsDao = friendsDao;
    }

    public List<User> getUsers() {
        return storageUser.getUsers();
    }

    public User getUserId(Long id) {
        return storageUser.getUserId(id);
    }

    public User createUser(User user) {
        return storageUser.createUser(user);
    }

    public User updateUser(User user) {
        return storageUser.updateUser(user);
    }

    public void addFriend(Long userId, Long friendId) {
        if (userId <= 0 || friendId <= 0) {
            throw new ObjectNotFoundException("id пользователя или id друга указаны не верно. id должен быть больше 0");
        }
        friendsDao.addFriend(userId,friendId);
    }

    public void deleteFriend(Long userId, Long friendId) {
        if (userId <= 0 || friendId <= 0) {
            throw new ObjectNotFoundException("id пользователя или id друга указаны не верно. id должен быть больше 0");
        }
        friendsDao.deleteFriend(userId,friendId);
    }

    public List<User> getFriends(Long id) {
        if (id <= 0) {
            throw new ObjectNotFoundException("id пользователя указан не верно. id должен быть больше 0");
        }
        return storageUser.getFriends(id);
    }

    public List<User> getMutualFriends(Long id, Long otherId) {
        if (id <= 0 || otherId <= 0) {
            throw new ObjectNotFoundException("id должен быть больше 0");
        }
        return storageUser.getMutualFriends(id,otherId);

    }
}
