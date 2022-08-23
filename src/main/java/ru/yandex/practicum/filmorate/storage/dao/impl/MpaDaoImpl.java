package ru.yandex.practicum.filmorate.storage.dao.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.dao.MpaDao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Slf4j
@Component
public class MpaDaoImpl implements MpaDao {

    private JdbcTemplate jdbcTemplate;

    public MpaDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Mpa findById(Integer id) {
        final String sqlQuery = "select * from MPA where MPA_ID = ?";
        final Mpa mpa = jdbcTemplate.queryForObject(sqlQuery,MpaDaoImpl::makeMpa,id);
        if(mpa.getId()!= id) {
            log.info("МРА с идентификатором {} не найден.", id);
            throw new ObjectNotFoundException("id отсутствует или не найден");
        }
        log.info("Найден МРА: {}", mpa);
        return mpa;
    }

    @Override
    public List<Mpa> findAll() {
        final String sqlQuery = "select * from MPA";
        final List<Mpa> mpas = jdbcTemplate.query(sqlQuery, MpaDaoImpl::makeMpa);
        return mpas;
    }

    static Mpa makeMpa(ResultSet rs, int rowNum) throws SQLException {
        return new Mpa(rs.getInt("MPA_ID"),
                rs.getString("MPA_NAME"));
    }
}
