package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.StorageUser;

@Service
public class UserService {

    @Autowired
    StorageUser storageUser;


    public void addFriend(int userId, int friendId) {
//        User user = storageUser.getUser(userId);
//        User friend = storageUser.getUser(friendId);
//        //TODO check userId and friendId
//        storageUser.addFriend(user,friend);
    }

    public void deleteFriend(int userId, int friendId) {
//        User user = storageUser.getUser(userId);
//        User friend = storageUser.getUser(friendId);
//        //TODO check userId and friendId
//        storageUser.deleteFriend(user,friend);
    }
}
