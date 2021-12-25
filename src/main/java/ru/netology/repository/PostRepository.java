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
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class PostRepository {
    private ConcurrentMap<Long, Post> postMap = new ConcurrentHashMap<>();
    private AtomicLong id = new AtomicLong(0);

    public List<Post> all() {
        ArrayList<Post> posts = new ArrayList<>();
        for (Post post:postMap.values()){
            if (!post.isArchived()){
                posts.add(post);
            }
        }
        return posts;
    }

    public Optional<Post> getById(long id) {
        Post post = postMap.get(id);
        if (post==null || post.isArchived()) {
            return Optional.empty();
        }
        return Optional.of(post);
    }

    public Optional<Post> save(Post post) {
        if (post.getId() == 0) {
            return Optional.of(createPost(post));
        } else {
            if (postMap.containsValue(post)) {
                long key = getKeyByValue(postMap, post);
                Post currentPost = postMap.get(key);
                if (currentPost.isArchived()){
                    return null;
                }
                currentPost.replace(post);
                return Optional.of(currentPost);
            } else {
                return Optional.of(createPost(post));
            }
        }
    }

    public Post removeById(long id) {
        Long idPost = getKeyByIdValue(postMap, id);
        if (null != idPost) {
            Post post = postMap.get(idPost);
            post.setArchived(true);
            return post;
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
        post.setArchived(false);
        postMap.put(currentId, post);
        return post;
    }
}
