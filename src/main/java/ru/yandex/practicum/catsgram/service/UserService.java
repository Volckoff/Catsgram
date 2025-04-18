package ru.yandex.practicum.catsgram.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.catsgram.exception.ConditionsNotMetException;
import ru.yandex.practicum.catsgram.exception.DuplicatedDataException;
import ru.yandex.practicum.catsgram.model.User;

import java.time.Instant;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class UserService {

    private final Map<Long, User> users = new HashMap<>();

    public Collection<User> findAll() {
        return users.values();
    }


    public Optional<User> findUserById(Long id) {
        return Optional.ofNullable(users.get(id));
    }


    public User create(User user) {
        if (user.getEmail() == null) {
            throw new ConditionsNotMetException("Имейл должен быть указан");
        }
        if (users.values().stream().anyMatch(u -> u.getEmail().equals(user.getEmail()))) {
            throw new DuplicatedDataException("Этот имейл уже используется");
        }
        user.setId(getNextId());
        user.setRegistrationDate(Instant.now());
        users.put(user.getId(), user);
        return user;
    }


    private long getNextId() {
        return users.keySet()
                .stream()
                .mapToLong(id -> id)
                .max()
                .orElse(0) + 1;
    }


    public User update(User updatedUser) {
        if (updatedUser.getId() == null) {
            throw new ConditionsNotMetException("Id должен быть указан");
        }
        User existingUser = users.get(updatedUser.getId());
        if (existingUser == null) {
            throw new ConditionsNotMetException("Пользователь с указанным ID не найден");
        }
        if (updatedUser.getEmail() != null && !updatedUser.getEmail().isEmpty()) {
            boolean isEmailUsed = users.values().stream()
                    .anyMatch(u -> !u.getId().equals(updatedUser.getId()) &&
                            u.getEmail().equals(updatedUser.getEmail()));
            if (isEmailUsed) {
                throw new DuplicatedDataException("Этот имейл уже используется");
            }
            existingUser.setEmail(updatedUser.getEmail());
        }
        if (updatedUser.getUsername() != null && !updatedUser.getUsername().isEmpty()) {
            existingUser.setUsername(updatedUser.getUsername());
        }
        if (updatedUser.getPassword() != null && !updatedUser.getPassword().isEmpty()) {
            existingUser.setPassword(updatedUser.getPassword());
        }
        return existingUser;
    }
}
