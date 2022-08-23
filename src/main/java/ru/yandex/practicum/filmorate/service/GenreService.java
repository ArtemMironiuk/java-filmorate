package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.dao.impl.GenreDaoImpl;

import java.util.List;

@Service
public class GenreService {

    private GenreDaoImpl genreDao;

    public GenreService(GenreDaoImpl genreDao) {
        this.genreDao = genreDao;
    }

    public Genre findById(Integer id) {
        return genreDao.findById(id);
    }

    public List<Genre> findAll() {
        return genreDao.findAll();
    }
}
