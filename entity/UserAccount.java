package entity;

import java.lang.*;
import java.time.LocalDate;
import java.util.*;

public class UserAccount extends Account {
    private String displayName;
    private String email;
    private String bio;
    private LocalDate dateOfBirth;
    private TreeSet<Integer> postIds = new TreeSet<Integer>();
    public UserAccount() {

    }

    public UserAccount(String username, String password, String displayName, String email, String bio, LocalDate dateOfBirth) {
        super(username, password);
        this.displayName = displayName;
        this.email = email;
        this.bio = bio;
        this.dateOfBirth = dateOfBirth;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
    public String getDisplayName() {
        return displayName;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public String getEmail() {
        return email;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }
    public String getBio() {
        return bio;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }
    public void setPostIds(TreeSet<Integer> postIds){
        this.postIds = postIds;
    }
    public TreeSet<Integer> getPostIds(){
        return postIds;
    }
    public void addPost(int postId){
        postIds.add(postId);
    }
    public void removePost(int postId){
        postIds.remove(postId);
    }
}
