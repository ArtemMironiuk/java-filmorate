package ru.yandex.practicum.filmorate.model;

import lombok.*;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Film {

    private int id;
    @NotBlank
    private String name;
    private String description;
    private LocalDate releaseDate;
    private int duration;
}
