package ru.netology.repository;

import ru.netology.model.Post;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

public class PostRepository {
    private ConcurrentMap<Long, Post> postMap = new ConcurrentHashMap<>();
    private AtomicLong id = new AtomicLong(0);

    public List<Post> all() {
        return new ArrayList(postMap.values());
    }

    public Optional<Post> getById(long id) {
        return Optional.of(postMap.get(id));
    }

    public Post save(Post post) {
        if (post.getId() == 0) {
            return createPost(post);
        } else {
            if (postMap.containsValue(post)) {
                long key = getKeyByValue(postMap, post);
                postMap.replace(key, post);
                return postMap.get(key);
            } else {
                return createPost(post);
            }
        }
    }

    public Post removeById(long id) {
        Long idPost = getKeyByIdValue(postMap, id);
        if (null != idPost) {
            return postMap.remove(idPost);
        }
        return null;
    }

    private Long getKeyByIdValue(Map<Long, Post> map, long idValue) {
        for (Map.Entry<Long, Post> entry : map.entrySet()) {
            if (idValue == entry.getValue().getId()) {
                return entry.getKey();
            }
        }
        return null;
    }

    private Long getKeyByValue(Map<Long, Post> map, Post value) {
        for (Map.Entry<Long, Post> entry : map.entrySet()) {
            if (Objects.equals(value, entry.getValue())) {
                return entry.getKey();
            }
        }
        return null;
    }

    private Post createPost(Post post) {
        long currentId = id.incrementAndGet();
        post.setId(currentId);
        postMap.put(currentId, post);
        return post;
    }
}
