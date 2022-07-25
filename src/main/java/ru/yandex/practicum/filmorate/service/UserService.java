package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.StorageUser;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserService {

    StorageUser storageUser;

    public UserService(StorageUser storageUser) {
        this.storageUser = storageUser;
    }


    /**
     * Добавление в друзья.
     * @param userId
     * @param friendId
     */
    public void addFriend(Long userId, Long friendId) {
        if (userId <= 0 || friendId <= 0) {
            throw new ObjectNotFoundException("id пользователя или id друга указаны не верно. id должен быть больше 0");
        }
        User user = storageUser.getUserId(userId);
        User friend = storageUser.getUserId(friendId);
        user.getFriendIds().add(friend.getId());
        friend.getFriendIds().add(user.getId());
    }

    /**
     * Удаление из друзей.
     * @param userId
     * @param friendId
     */
    public void deleteFriend(Long userId, Long friendId) {
        if (userId <= 0 || friendId <= 0) {
            throw new ObjectNotFoundException("id пользователя или id друга указаны не верно. id должен быть больше 0");
        }
        User user = storageUser.getUserId(userId);
        User friend = storageUser.getUserId(friendId);
        user.getFriendIds().remove(friend.getId());
        friend.getFriendIds().remove(user.getId());
    }

    /**
     * Получение списка пользователей, являющихся его друзьями
     * @param id
     * @return
     */
    public List<User> getFriends(Long id) {
        if (id <= 0) {
            throw new ObjectNotFoundException("id пользователя указан не верно. id должен быть больше 0");
        }
        User user = storageUser.getUserId(id);
        Set<Long> friends = user.getFriendIds();
        if(friends.isEmpty()) {
            throw new ObjectNotFoundException("У пользователя нет друзей");
        }
        List<User> users = new ArrayList<>();
        for (Long friend : friends) {
           users.add(storageUser.getUserId(friend));
        }
        return users;
    }

    /**
     * Получение списка друзей, общих с другим пользователем.
     * @param id
     * @param otherId
     * @return
     */
    public List<User> getMutualFriends(Long id, Long otherId) {
        if (id <= 0 || otherId <= 0) {
            throw new ObjectNotFoundException("id должен быть больше 0");
        }
        User user = storageUser.getUserId(id);
        User otherUser = storageUser.getUserId(otherId);
        Set<Long> listFriendsUser;
        Set<Long> listFriendsOtherUser;
        if (user.getFriendIds() != null && otherUser.getFriendIds() != null) {
            listFriendsUser = user.getFriendIds();
            listFriendsOtherUser = otherUser.getFriendIds();
        } else {
            throw new ObjectNotFoundException("у одного из пользователей список друзей пуст");
        }
        Set<Long> listMutualFriends = new HashSet<>(listFriendsUser);
        listMutualFriends.retainAll(listFriendsOtherUser);
        List<User> users = new ArrayList<>();
        for (Long friend : listMutualFriends) {
            users.add(storageUser.getUserId(friend));
        }
        return users;
    }
}
