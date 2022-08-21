package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.dao.StorageUser;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Service
public class UserService {

    private final StorageUser storageUser;
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public UserService(@Qualifier("userDbStorage") StorageUser storageUser, JdbcTemplate jdbcTemplate) {
        this.storageUser = storageUser;
        this.jdbcTemplate =jdbcTemplate;
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
    /**
     * Добавление в друзья.
     * @param userId
     * @param friendId
     */
    public void addFriend(Long userId, Long friendId) {
        if (userId <= 0 || friendId <= 0) {
            throw new ObjectNotFoundException("id пользователя или id друга указаны не верно. id должен быть больше 0");
        }
        SqlRowSet userRows = jdbcTemplate.queryForRowSet( "select * from FRIENDS where USER_ID = ?", userId);
        if (userRows.next()){
            jdbcTemplate.update("update FRIENDS set IS_CONFIRMED = true where USER_ID =? and FRIEND_ID =?",
                    friendId,userId);
            jdbcTemplate.update("INSERT INTO FRIENDS(USER_ID, FRIEND_ID, IS_CONFIRMED) VALUES(?, ?,?)",
                    userId,friendId,true);

        } else {
            jdbcTemplate.update("INSERT INTO FRIENDS(USER_ID, FRIEND_ID) VALUES(?, ?)", userId,friendId);

        }
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
        jdbcTemplate.update("DELETE FROM FRIENDS WHERE USER_ID = ? and FRIEND_ID = ?",userId,friendId);
        SqlRowSet userRows = jdbcTemplate.queryForRowSet( "SELECT * FROM FRIENDS WHERE USER_ID = ? and FRIEND_ID = ?",
                friendId,userId);
        if (userRows.next()){
            jdbcTemplate.update("update FRIENDS set IS_CONFIRMED = false where USER_ID =? and FRIEND_ID =?",
                    friendId,userId);
        }
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
            List<User> friends = jdbcTemplate.query("select U.USER_ID, U.USER_NAME, U.LOGIN, U.EMAIL, U.BIRTHDAY " +
                            "FROM FRIENDS AS F INNER JOIN USERS AS U ON F.FRIEND_ID = U.USER_ID WHERE F.USER_ID = ?",
                    UserService::makeUser,id);
        return friends;
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
        List<Integer> friendsId = jdbcTemplate.queryForList("select FRIEND_ID from FRIENDS where USER_ID = ?",
                Integer.class, id);
        List<Integer> otherFriendsId = jdbcTemplate.queryForList("select FRIEND_ID from FRIENDS where USER_ID = ?",
                Integer.class ,otherId);
        List<User> users = new ArrayList<>();
        List<Integer> mutualFriends = new LinkedList<>();
        mutualFriends.addAll(friendsId);
        mutualFriends.retainAll(otherFriendsId);
        if(mutualFriends.isEmpty()) {
            return users;
        }
        for (Integer integer: mutualFriends) {
            users.add(storageUser.getUserId(Long.valueOf(integer)));
        }
        return users;

    }

    static User makeUser (ResultSet rs, int rowNum) throws SQLException {
        return new User(rs.getLong("USER_ID"),
                rs.getString("USER_NAME"),
                rs.getString("LOGIN"),
                rs.getString("EMAIL"),
                rs.getDate("BIRTHDAY").toLocalDate()
        );
    }
}
