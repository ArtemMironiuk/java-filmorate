package ru.yandex.practicum.filmorate.model;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Film {

    private int id;
    @NotEmpty
    private String name;
    private String description;
    private LocalDate releaseDate;
    @Positive
    private int duration;
}
