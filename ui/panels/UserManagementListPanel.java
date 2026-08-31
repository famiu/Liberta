package ui.panels;

import java.lang.*;
import entity.*;
import storage.*;

public class UserManagementListPanel extends ManagementListPanel{
    public UserManagementListPanel(){
        super();
        this.filterRows("");
    }

    protected void addMatchingRows(String search){
        for(UserAccount user: UserStorage.getAllUser().values()){
            String username = user.getUsername().toLowerCase();
            String displayName = user.getDisplayName().toLowerCase();
            String email = user.getEmail().toLowerCase();

            boolean usernameMatches = username.contains(search);
            boolean displayNameMatches = displayName.contains(search);
            boolean emailMatches = email.contains(search);
            boolean userMatches = usernameMatches || displayNameMatches || emailMatches;

            if(userMatches){
                this.add(new UserManagementRowPanel(user));
            }
        }
    }
}
