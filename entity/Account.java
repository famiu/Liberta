package entity;

import java.lang.*;

public class Account {
    private String username;
    private int passwordHash;

    public Account() {
    }

    public Account(String username, int passwordHash) {
        this.username = username;
        this.passwordHash = passwordHash;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    public String getUsername() {
        return username;
    }

    public void setPasswordHash(int passwordHash) {
        this.passwordHash = passwordHash;
    }
    public int getPasswordHash() {
        return passwordHash;
    }
}
