package ru.netology.repository;

import ru.netology.model.Post;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class PostRepository {
    private ConcurrentMap<Long, Post> postMap = new ConcurrentHashMap<>();

    public List<Post> all() {
        return new ArrayList(postMap.values()) ;
    }

    public Optional<Post> getById(long id) {
        return Optional.of(postMap.get(id));
    }

    public Post save(Post post) {
        if (postMap.containsKey(post.getId())) {
            postMap.replace(post.getId(), post);
        } else {
            postMap.put(post.getId(), post);
        }
        return post;
    }

    public Post removeById(long id) {
       return postMap.remove(id);
    }
}
