package ru.netology.repository;

import ru.netology.model.Post;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class PostRepository {
    private ConcurrentMap<Long, Post> postMap = new ConcurrentHashMap<>();
    private long id = 0;

    public List<Post> all() {
        return new ArrayList(postMap.values());
    }

    public Optional<Post> getById(long id) {
        return Optional.of(postMap.get(id));
    }

    public Post save(Post post) {
        if (post.getId() == 0) {
            createPost(post);
        } else {
            if (postMap.containsValue(post)) {
                long key = getKeyByValue(postMap, post);
                postMap.replace(key, post);
            } else {
                createPost(post);
            }
        }
        return post;
    }

    public Post removeById(long id) {
        return postMap.remove(id);
    }

    private <Long, Post> Long getKeyByValue(Map<Long, Post> map, Post value) {
        for (Map.Entry<Long, Post> entry : map.entrySet()) {
            if (Objects.equals(value, entry.getValue())) {
                return entry.getKey();
            }
        }
        return null;
    }

    private void createPost(Post post) {
        postMap.put(++id, post);
    }
}
