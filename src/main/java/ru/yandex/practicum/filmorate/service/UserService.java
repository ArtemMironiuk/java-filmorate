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


    public void addFriend(Long userId, Long friendId) {
        User user = storageUser.getUserId(userId);
        User friend = storageUser.getUserId(friendId);
        user.getFriendIds().add(friend.getId());
        friend.getFriendIds().add(user.getId());
        //TODO check userId and friendId
    }

    public void deleteFriend(Long userId, Long friendId) {
        User user = storageUser.getUserId(userId);
        User friend = storageUser.getUserId(friendId);
        user.getFriendIds().remove(friend.getId());
        friend.getFriendIds().remove(user.getId());
//        //TODO check userId and friendId
    }

    public List<User> getFriends(Long id) {
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

    public List<User> getMutualFriends(Long id, Long otherId) {
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
        if (listMutualFriends.isEmpty()) {
            throw new ObjectNotFoundException("общих друзей нет");
        }
        List<User> users = new ArrayList<>();
        for (Long friend : listMutualFriends) {
            users.add(storageUser.getUserId(friend));
        }
        return users;
    }
}
