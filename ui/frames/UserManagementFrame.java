package ui.frames;

import java.lang.*;
import javax.swing.*;

import ui.panels.*;

public class UserManagementFrame extends ManagementFrame {
    public UserManagementFrame() {
        super("User Management");

        ManagementHeaderPanel header = new ManagementHeaderPanel();
        header.addHeaderLabel("Username", 0, 200, 2.0);
        header.addHeaderLabel("Display Name", 1, 200, 2.0);
        header.addHeaderLabel("Email", 2, 300, 3.0);
        header.addHeaderLabel("Action", 3, 100, 1.0, SwingConstants.CENTER);

        this.setManagementContent(header, new UserManagementListPanel());
    }
}
