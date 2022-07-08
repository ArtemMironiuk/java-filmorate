//package ru.yandex.practicum.filmorate.controller;
//
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//import ru.yandex.practicum.filmorate.exception.ValidationException;
//import ru.yandex.practicum.filmorate.model.User;
//
//import java.time.LocalDate;
//
//class UserControllerTest {
//
//    @Test
//    public void validateEmail() {
//        final UserController userController = new UserController();
//        final User user = new User(1,"email.email.ru","login","admin", LocalDate.of(1995,06,8));
//        Assertions.assertThrows(ValidationException.class,() -> userController.validate(user));
//    }
//
//    @Test
//    public void validateLogin() {
//        final UserController userController = new UserController();
//        final User user = new User(1,"email@email.ru","","admin", LocalDate.of(1995,06,8));
//        Assertions.assertThrows(ValidationException.class,() -> userController.validate(user));
//    }
//
//    @Test
//    public void validateName() {
//        final UserController userController = new UserController();
//        final User user = new User(1,"email@email.ru","login","", LocalDate.of(1995,06,8));
//        userController.validate(user);
//        Assertions.assertEquals(user.getName(),user.getLogin(),"Name = Login");
//    }
//    @Test
//    public void validateBirthdate() {
//        final UserController userController = new UserController();
//        final User user = new User(1,"email.email.ru","login","admin", LocalDate.of(2023,06,8));
//        Assertions.assertThrows(ValidationException.class,() -> userController.validate(user));
//    }
//
//}