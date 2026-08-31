package ui.panels;

import java.lang.*;
import java.io.*;
import javax.swing.*;
import javax.swing.border.*;


import java.awt.*;
import java.awt.event.*;

import entity.*;
import storage.*;

import ui.*;
import ui.components.*;
import ui.dialogs.*;
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
            UserManageFrame userManage = new UserManageFrame();
            parentFrame.switchFrame(userManage);
        }
        else if (e.getSource() == PostManagementButton) {
            PostManageFrame postManage = new PostManageFrame();
            parentFrame.switchFrame(postManage);
        }
        else if (e.getSource() == logoutButton) {
            parentFrame.switchFrame(new LoginFrame());
        }
    }
}