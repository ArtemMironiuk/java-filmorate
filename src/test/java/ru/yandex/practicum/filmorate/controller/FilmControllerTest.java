//package ru.yandex.practicum.filmorate.controller;
//
//import org.junit.jupiter.api.Test;
//import ru.yandex.practicum.filmorate.exception.ValidationException;
//import ru.yandex.practicum.filmorate.model.Film;
//
//import java.time.LocalDate;
//
//import static org.junit.jupiter.api.Assertions.assertThrows;
//
//class FilmControllerTest {
//
//    @Test
//    public void validateName(){
//        final FilmController filmController = new FilmController();
//        final Film film = new Film(0,"","Описание", LocalDate.of(1893,05,8), 50);
//        assertThrows(ValidationException.class,() -> filmController.validate(film));
//    }
//
//    @Test
//    public void validateReleaseDate(){
//        final FilmController filmController = new FilmController();
//        final Film film = new Film(0,"Один дома","Описание", LocalDate.of(1896,05,8), 50);
//        assertThrows(ValidationException.class,() -> filmController.validate(film));
//    }
//
//    @Test
//    public void validateDuration(){
//        final FilmController filmController = new FilmController();
//        final Film film = new Film(0,"Один дома","Описание", LocalDate.of(1893,05,8), -50);
//        assertThrows(ValidationException.class,() -> filmController.validate(film));
//    }
//
//    @Test
//    public void validateDescription(){
//        final FilmController filmController = new FilmController();
//        final Film film = new Film(0,"Один дома","ООООООООООООООООООООООООООООООООООООООООООООООООО" +
//                "ООООООООООООООООООООООООООООООООООООООООООООООООООООООООООООООООООООООООООООООООООООООООООООООООООООО" +
//                "ОООООООООООООООООООППППППИИИИИИИИИИИИИИСССССССССААНИЕ", LocalDate.of(1893,05,8), 50);
//        assertThrows(ValidationException.class,() -> filmController.validate(film));
//    }
//
//}