package entity;

import java.lang.*;

public class Account {
    private String username;
    private String password;

    public Account() {
    }

    public Account(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    public String getUsername() {
        return username;
    }

    public void setpassword(String password) {
        this.password = password;
    }
    public int getpassword() {
        return password;
    }
}
