package ru.yandex.practicum.catsgram.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.catsgram.exception.NotFoundException;
import ru.yandex.practicum.catsgram.exception.ParameterNotValidException;
import ru.yandex.practicum.catsgram.model.Post;
import ru.yandex.practicum.catsgram.service.PostService;

import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }


    @GetMapping
    public List<Post> findAll(
            @RequestParam(defaultValue = "0") int from,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "desc") String sort) {
        try {
            SortOrder sortOrder = SortOrder.from(sort);
        } catch (IllegalArgumentException e) {
            throw new ParameterNotValidException("sort", "Некорректное значение сортировки. " +
                    "Допустимые значения: 'asc', 'desc'.");
        }
        if (size <= 0) {
            throw new ParameterNotValidException("size", "Некорректный размер выборки. " +
                    "Размер должен быть больше нуля.");
        }
        if (from < 0) {
            throw new ParameterNotValidException("from", "Некорректное значение начальной позиции. " +
                    "Значение не может быть меньше нуля.");
        }
        SortOrder sortOrder = SortOrder.from(sort);
        return postService.findAll(from, size, sortOrder);
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Post create(@RequestBody Post post) {
        return postService.create(post);
    }


    @PutMapping
    public Post update(@RequestBody Post newPost) {
        return postService.update(newPost);
    }


    @GetMapping("/{id}")
    public Post getPostById(@PathVariable("id") Long id) {
        return postService.findById(id)
                .orElseThrow(() -> new NotFoundException("Пост с id = " + id + " не найден"));
    }
}