package entity;

import java.lang.*;
import java.time.*;
import java.util.*;

public class Post {
    private int postId;
    private String author;
    private String content;
    private LocalDateTime timestamp;
    private TreeSet<String> likes = new TreeSet<String>();

    private static int postCounter = 0;

    public Post() {
        postCounter++;
        this.postId = postCounter;
        this.timestamp = LocalDateTime.now();
    }

    public Post(String author, String content) {
        this();
        this.author = author;
        this.content = content;
    }

    public Post(int postId, String author, String content, LocalDateTime timestamp) {
        this.postId = postId;
        this.author = author;
        this.content = content;
        this.timestamp = timestamp;

        if (postId > postCounter) {
            postCounter = postId;
        }
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }
    public int getPostId() {
        return postId;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
    public String getAuthor() {
        return author;
    }

    public void setContent(String content) {
        this.content = content;
    }
    public String getContent() {
        return content;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public TreeSet<String> getLikes() {
        return likes;
    }

    public void addLike(String username) {
        likes.add(username);
    }

    public void removeLike(String username) {
        likes.remove(username);
    }

    public void toggleLike(String username) {
        if (isLikedBy(username)) {
            removeLike(username);
        }
        else {
            addLike(username);
        }
    }

    public boolean isLikedBy(String username) {
        return likes.contains(username);
    }

    public int getLikeCount() {
        return likes.size();
    }

    public static void setPostCounter(int postCounter) {
        Post.postCounter = postCounter;
    }

    public static int getPostCounter() {
        return postCounter;
    }
}
