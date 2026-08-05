package entity;

import java.lang.*;

public class AdminAccount extends Account {
    public AdminAccount() {
    }

    public AdminAccount(String username, int passwordHash) {
        super(username, passwordHash);
    }
}
