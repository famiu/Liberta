import java.lang.*;

import storage.*;

import ui.*;
import ui.frames.*;

public class Start {
    public static void main(String[] args) {
        Theme.apply();

        String username = SessionStorage.getLoggedInUser();

        if (username != null && UserStorage.checkUser(username)) {
            HomeFrame home = new HomeFrame(username);
            home.setVisible(true);
        }
        else {
            if (username != null) {
                // Clear invalid logged in user from the session storage
                SessionStorage.clearLoggedInUser();
            }
            LoginFrame login = new LoginFrame();
            login.setVisible(true);
        }
    }
}
