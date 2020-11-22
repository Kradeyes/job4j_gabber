package ru.job4j.html;

import java.util.List;
import java.util.function.Predicate;

public interface Store {
    void save(Post post);

    Post findById(String id);

    List<Post> getAll();
}