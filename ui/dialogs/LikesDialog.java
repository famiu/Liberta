package ui.dialogs;

import java.lang.*;
import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import entity.*;
import storage.*;

import ui.*;
import ui.components.*;
import ui.frames.*;
import ui.panels.*;

public class LikesDialog extends LibertaDialog implements ActionListener {
    private static final int PROFILE_PICTURE_SIZE = 40;

    private LibertaFrame parentFrame;
    private String signedInUsername;
    private LibertaButton closeButton;

    public LikesDialog(LibertaFrame parentFrame, Post post, String signedInUsername) {
        super(parentFrame, "Liked by", 460, 440, new BorderLayout(0, 15));

        this.parentFrame = parentFrame;
        this.signedInUsername = signedInUsername;

        ScrollablePanel likesPanel = new ScrollablePanel(true);
        likesPanel.setLayout(new BoxLayout(likesPanel, BoxLayout.Y_AXIS));
        likesPanel.setBorder(BorderFactory.createEmptyBorder(5, 8, 5, 8));
        likesPanel.setBackground(Theme.BACKGROUND);

        if (post.getLikes().isEmpty()) {
            JLabel noLikesLabel = new JLabel("No likes yet", SwingConstants.CENTER);
            noLikesLabel.setForeground(Theme.TEXT_MUTED);
            likesPanel.setLayout(new BorderLayout());
            likesPanel.add(noLikesLabel, BorderLayout.CENTER);
        }
        else {
            for (String username : post.getLikes()) {
                JPanel userPanel = createUserPanel(username);
                if (userPanel != null) {
                    likesPanel.add(userPanel);
                }
            }
        }

        JScrollPane scrollPane = new JScrollPane(likesPanel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(BorderFactory.createLineBorder(Theme.BACKGROUND3));
        scrollPane.getViewport().setBackground(Theme.BACKGROUND);

        JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();
        verticalScrollBar.setUI(new LibertaScrollBarUI(Theme.TEXT_MUTED, Theme.BACKGROUND2));
        verticalScrollBar.setPreferredSize(new Dimension(14, 0));
        verticalScrollBar.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
        verticalScrollBar.setBackground(Theme.BACKGROUND);

        closeButton = new LibertaButton("Close");
        closeButton.setColors(Theme.ACCENT1, Theme.ACCENT2, Theme.TEXT, Theme.TEXT, true);
        closeButton.setMargin(new Insets(10, 18, 10, 18));
        closeButton.addActionListener(this);
        this.setDefaultButton(closeButton);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        buttonPanel.setOpaque(false);
        buttonPanel.add(closeButton);

        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);
    }

    private JPanel createUserPanel(String username) {
        UserAccount user = UserStorage.getUser(username);
        if (user == null) {
            return null;
        }

        File profilePictureFile = UserStorage.getUserProfilePicture(username);
        ImageIcon profilePicture = new ImageIcon(profilePictureFile.getPath());
        Image scaledImage = profilePicture.getImage().getScaledInstance(PROFILE_PICTURE_SIZE, PROFILE_PICTURE_SIZE,
            Image.SCALE_SMOOTH);
        profilePicture.setImage(scaledImage);

        LibertaButton profilePictureButton = new LibertaButton(profilePicture);
        profilePictureButton.setColors(Theme.BACKGROUND, Theme.BACKGROUND2, Theme.TEXT, Theme.TEXT, true);
        profilePictureButton.setBorder(null);
        profilePictureButton.setAlignmentY(Component.CENTER_ALIGNMENT);
        profilePictureButton.setActionCommand(username);
        profilePictureButton.addActionListener(this);

        LibertaButton displayNameButton = new LibertaButton(user.getDisplayName());
        displayNameButton.setBorder(null);
        displayNameButton.setActionCommand(username);
        displayNameButton.addActionListener(this);

        LibertaButton usernameButton = new LibertaButton("@" + username);
        usernameButton.setColors(Theme.BACKGROUND, Theme.BACKGROUND, Theme.TEXT_MUTED, Theme.ACCENT1, false);
        usernameButton.setBorder(null);
        usernameButton.setFont(Theme.MAIN_FONT);
        usernameButton.setActionCommand(username);
        usernameButton.addActionListener(this);

        FlowLayout identityLayout = new FlowLayout(FlowLayout.LEFT, 6, 0);
        identityLayout.setAlignOnBaseline(true);
        JPanel identityPanel = new JPanel(identityLayout);
        identityPanel.setOpaque(false);
        identityPanel.add(displayNameButton);
        identityPanel.add(usernameButton);
        identityPanel.setMaximumSize(identityPanel.getPreferredSize());
        identityPanel.setAlignmentY(Component.CENTER_ALIGNMENT);

        JPanel userPanel = new JPanel();
        userPanel.setLayout(new BoxLayout(userPanel, BoxLayout.X_AXIS));
        userPanel.setBackground(Theme.BACKGROUND);
        userPanel.setBorder(BorderFactory.createEmptyBorder(8, 10, 8, 10));
        userPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 64));
        userPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        userPanel.add(profilePictureButton);
        userPanel.add(Box.createHorizontalStrut(8));
        userPanel.add(identityPanel);
        userPanel.add(Box.createHorizontalGlue());

        return userPanel;
    }

    public void showDialog() {
        this.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == closeButton) {
            this.dispose();
        }
        else {
            String profileUsername = e.getActionCommand();
            this.dispose();
            parentFrame.switchFrame(new ProfileFrame(signedInUsername, profileUsername));
        }
    }
}
