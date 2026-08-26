package entity;

import java.lang.*;
import java.time.*;

public class Post {
    private int postId;
    private String author;
    private String content;
    private LocalDateTime timestamp;

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
}

