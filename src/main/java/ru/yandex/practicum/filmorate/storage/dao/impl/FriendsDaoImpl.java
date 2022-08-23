package ru.yandex.practicum.filmorate.storage.dao.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.storage.dao.FriendsDao;

@Slf4j
@Component
public class FriendsDaoImpl implements FriendsDao {

    private JdbcTemplate jdbcTemplate;

    public FriendsDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void addFriend(Long userId, Long friendId) {
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

    @Override
    public void deleteFriend(Long userId, Long friendId) {
        jdbcTemplate.update("DELETE FROM FRIENDS WHERE USER_ID = ? and FRIEND_ID = ?",userId,friendId);
        SqlRowSet userRows = jdbcTemplate.queryForRowSet( "SELECT * FROM FRIENDS WHERE USER_ID = ? and FRIEND_ID = ?",
                friendId,userId);
        if (userRows.next()){
            jdbcTemplate.update("update FRIENDS set IS_CONFIRMED = false where USER_ID =? and FRIEND_ID =?",
                    friendId,userId);
        }
    }
}
