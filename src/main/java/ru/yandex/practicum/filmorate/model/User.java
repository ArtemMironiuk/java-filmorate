package ru.yandex.practicum.filmorate.model;

import lombok.*;
import net.minidev.json.annotate.JsonIgnore;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Past;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class User {

    private Long id;
    @NotEmpty
    @Email
    private String email;
    @NotEmpty
    @NotBlank
    private String login;
    private String name;
    @Past
    private LocalDate birthday;
    @JsonIgnore
    private Set<Long> friendIds = new HashSet<>();

}
