package ru.netology.repository;

import org.springframework.stereotype.Repository;
import ru.netology.model.Post;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Repository
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

    private void createPost(Post post) {
        postMap.put(++id, post);
    }
}
