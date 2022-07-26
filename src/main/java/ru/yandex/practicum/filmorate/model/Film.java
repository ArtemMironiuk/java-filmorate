package ru.yandex.practicum.filmorate.model;

import lombok.*;
import net.minidev.json.annotate.JsonIgnore;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Film {

    private Long id;
    @NotEmpty
    private String name;
    private String description;
    private LocalDate releaseDate;
    @Positive
    private int duration;
    @JsonIgnore
    private Set<Long> userIds = new HashSet<>();
}
