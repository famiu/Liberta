package ui.panels;

import java.lang.*;
import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import entity.*;
import storage.*;

import ui.*;
import ui.components.*;
import ui.dialogs.*;
import ui.frames.*;

public class UserSidebarPanel extends SidebarPanel implements ActionListener {
    private static final ImageIcon homeIcon = new ImageIcon("./assets/icons/png/32/home-feed-button.png");
    private static final ImageIcon createPostIcon = new ImageIcon("./assets/icons/png/32/create-post-button.png");

    private LibertaButton homeButton;
    private LibertaButton myProfileButton;
    private LibertaButton createPostButton;

    public UserSidebarPanel(LibertaFrame parentFrame, String username, String activeItem) {
        super(parentFrame, username, activeItem);
        homeButton = new LibertaButton("Home", homeIcon);
        homeButton.setMargin(new Insets(2, 2, 2, 2));
        homeButton.addActionListener(this);
        this.add(createNavigationPanel(homeButton, activeItem.equals("Home")));
        this.add(Box.createVerticalStrut(20));

        File profilePictureFile = UserStorage.getUserProfilePicture(username);
        ImageIcon profilePicture = new ImageIcon(profilePictureFile.getPath());
        Image profilePictureImage = profilePicture.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH);
        profilePicture.setImage(profilePictureImage);
        myProfileButton = new LibertaButton("My Profile", profilePicture);
        myProfileButton.setMargin(new Insets(2, 2, 2, 2));
        myProfileButton.addActionListener(this);
        this.add(createNavigationPanel(myProfileButton, activeItem.equals("My Profile")));
        this.add(Box.createVerticalStrut(20));

        createPostButton = new LibertaButton("Create Post", createPostIcon);
        createPostButton.setColors(Theme.TEXT, Theme.TEXT_MUTED, Theme.BACKGROUND, Theme.BACKGROUND, true);
        createPostButton.setMargin(new Insets(10, 16, 10, 24));
        createPostButton.addActionListener(this);
        this.add(createPostButton);
        this.add(Box.createVerticalGlue());
        
        logoutButton = new LibertaButton("Logout", logoutIcon);
        this.add(logoutButton);
        logoutButton.addActionListener(this);
    }
    
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == homeButton) {
            HomeFrame home = new HomeFrame(username);
            parentFrame.switchFrame(home);
        }
        else if (e.getSource() == myProfileButton) {
            ProfileFrame profile = new ProfileFrame(username);
            parentFrame.switchFrame(profile);
        }
        else if (e.getSource() == createPostButton) {
            CreatePostDialog createPostDialog = new CreatePostDialog(parentFrame);
            String postContent = createPostDialog.showDialog();

            if (postContent != null && !postContent.isEmpty()) {
                Post post = new Post(username, postContent);
                PostStorage.addPost(post);

                LibertaMessageDialog messageDialog = new LibertaMessageDialog(parentFrame, "Create Post",
                        "Post created successfully.");
                messageDialog.showDialog();

                // Refresh My Profile if it it's the current frame so the newly created post is shown
                if (parentFrame.getTitle().equals("My Profile")) {
                    ProfileFrame profile = new ProfileFrame(username);
                    parentFrame.switchFrame(profile);
                }
            }
        }
        else if (e.getSource() == logoutButton) {
            SessionStorage.clearLoggedInUser();
            parentFrame.switchFrame(new LoginFrame());
        }
    }
}
