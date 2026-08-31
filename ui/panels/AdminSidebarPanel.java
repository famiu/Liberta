package ui.panels;

import java.lang.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import ui.components.*;
import ui.frames.*;

public class AdminSidebarPanel extends SidebarPanel implements ActionListener {
    private LibertaButton UserManagementButton;
    private LibertaButton PostManagementButton;

    public AdminSidebarPanel(LibertaFrame parentFrame, String username, String activeItem) {
        super(parentFrame, username, activeItem);
        UserManagementButton = new LibertaButton("User Management");
        UserManagementButton.setMargin(new Insets(2, 2, 2, 2));
        UserManagementButton.addActionListener(this);
        this.add(createNavigationPanel(UserManagementButton, activeItem.equals("User Management")));
        this.add(Box.createVerticalStrut(20));

        PostManagementButton = new LibertaButton("Post Management");
        PostManagementButton.setMargin(new Insets(2, 2, 2, 2));
        PostManagementButton.addActionListener(this);
        this.add(createNavigationPanel(PostManagementButton, activeItem.equals("Post Management")));
        this.add(Box.createVerticalGlue());

        logoutButton = new LibertaButton("Logout", logoutIcon);
        this.add(logoutButton);
        logoutButton.addActionListener(this);
    }
    
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == UserManagementButton) {
            UserManagementFrame userManagement = new UserManagementFrame();
            parentFrame.switchFrame(userManagement);
        }
        else if (e.getSource() == PostManagementButton) {
            PostManagementFrame postManagement = new PostManagementFrame();
            parentFrame.switchFrame(postManagement);
        }
        else if (e.getSource() == logoutButton) {
            parentFrame.switchFrame(new LoginFrame());
        }
    }
}
