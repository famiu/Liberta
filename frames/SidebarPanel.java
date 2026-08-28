package frames;

import java.lang.*;
import java.io.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

import entity.*;
import storage.*;

public class SidebarPanel extends JPanel implements ActionListener {
    private static final ImageIcon homeIcon = new ImageIcon("./assets/icons/png/32/home-feed-button.png");
    private static final ImageIcon logoutIcon = new ImageIcon("./assets/icons/png/32/logout-button.png");
    private static final ImageIcon createPostIcon = new ImageIcon("./assets/icons/png/32/create-post-button.png");

    LibertaFrame parentFrame;
    String username;

    LibertaButton homeButton;
    LibertaButton myProfileButton;
    LibertaButton createPostButton;
    LibertaButton logoutButton;

    public SidebarPanel(LibertaFrame parentFrame, String username, String activeItem) {
        this.parentFrame = parentFrame;
        this.username = username;

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBackground(Theme.BACKGROUND);
        Border padding = BorderFactory.createEmptyBorder(15, 15, 15, 15);
        Border separator = BorderFactory.createMatteBorder(0, 0, 0, 1, Theme.BACKGROUND3);
        this.setBorder(BorderFactory.createCompoundBorder(separator, padding));

        homeButton = new LibertaButton("Home", homeIcon);
        homeButton.setMargin(new Insets(2, 2, 2, 2));
        homeButton.setAlignmentY(Component.CENTER_ALIGNMENT);
        homeButton.addActionListener(this);
        this.add(createNavigationPanel(homeButton, activeItem.equals("Home")));
        this.add(Box.createVerticalStrut(20));

        File profilePictureFile = UserStorage.getUserProfilePicture(username);
        ImageIcon profilePicture = new ImageIcon(profilePictureFile.getPath());
        Image profilePictureImage = profilePicture.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH);
        profilePicture.setImage(profilePictureImage);
        myProfileButton = new LibertaButton("My Profile", profilePicture);
        myProfileButton.setMargin(new Insets(2, 2, 2, 2));
        myProfileButton.setAlignmentY(Component.CENTER_ALIGNMENT);
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
        logoutButton.addActionListener(this);
        this.add(logoutButton);
    }

    private static JPanel createNavigationPanel(LibertaButton button, boolean active) {
        Dimension indicatorSize = new Dimension(3, 28);
        JPanel indicator = new JPanel();
        if (active) {
            indicator.setBackground(Theme.ACCENT1);
        }
        else {
            indicator.setBackground(Theme.BACKGROUND);
        }
        indicator.setMinimumSize(indicatorSize);
        indicator.setPreferredSize(indicatorSize);
        indicator.setMaximumSize(indicatorSize);
        indicator.setAlignmentY(Component.CENTER_ALIGNMENT);

        JPanel navigationPanel = new JPanel();
        navigationPanel.setLayout(new BoxLayout(navigationPanel, BoxLayout.X_AXIS));
        navigationPanel.setOpaque(false);
        navigationPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        button.setAlignmentY(Component.CENTER_ALIGNMENT);
        navigationPanel.add(indicator);
        navigationPanel.add(Box.createHorizontalStrut(8));
        navigationPanel.add(button);
        navigationPanel.setMaximumSize(navigationPanel.getPreferredSize());
        return navigationPanel;
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == homeButton) {
            Home home = new Home(username);
            parentFrame.switchFrame(home);
        }
        else if (e.getSource() == myProfileButton) {
            Profile profile = new Profile(username);
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
                    Profile profile = new Profile(username);
                    parentFrame.switchFrame(profile);
                }
            }
        }
        else if (e.getSource() == logoutButton) {
            // TODO(famiu): handle logout button click
        }
    }
}
