package ru.yandex.practicum.catsgram.model;

import java.time.Instant;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(of = "email")
public class User {
    Long id;
    String username;
    String email;
    String password;
    Instant registrationDate;
}
