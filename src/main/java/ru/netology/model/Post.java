package ru.netology.model;


public class Post {
    private long id;
    private String content;
    private Boolean archived;

    public Post() {
    }

    public Post(long id, String content) {
        this.id = id;
        this.content = content;
    }

    public Boolean isArchived() {
        return archived;
    }

    public void setArchived(boolean archived) {
        this.archived = archived;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Post replace(Post post) {
        if (post.content != null && !post.content.equals(this.content)) {
            this.content = post.content;
        }
        if (post.archived != null && post.archived != this.archived) {
            this.archived = post.archived;
        }
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Post post = (Post) o;
        return id == post.id;
    }


}
