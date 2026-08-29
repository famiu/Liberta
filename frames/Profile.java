package frames;

import java.lang.*;
import java.io.*;
import java.util.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.*;

import entity.*;
import storage.*;

public class Profile extends LibertaFrame implements ActionListener {
    private String signedInUsername;
    private String profileUsername;

    private SidebarPanel sidebar;
    private FeedPanel feedPanel;
    private JPanel headerPanel;

    private LibertaButton editProfileButton;
    private LibertaButton deleteAccountButton;

    public Profile(String signedInUsername) {
        this(signedInUsername, signedInUsername);
    }

    public Profile(String signedInUsername, String profileUsername) {
        super(getProfileTitle(signedInUsername, profileUsername), new BorderLayout());

        this.signedInUsername = signedInUsername;
        this.profileUsername = profileUsername;

        String activeItem;
        if (signedInUsername.equals(profileUsername)) {
            activeItem = "My Profile";
        }
        else {
            activeItem = "Home";
        }

        sidebar = new SidebarPanel(this, signedInUsername, activeItem);
        feedPanel = new FeedPanel(getProfilePosts(profileUsername), signedInUsername);
        headerPanel = createHeaderPanel();

        JPanel profilePanel = new JPanel(new BorderLayout());
        profilePanel.setBackground(Theme.BACKGROUND);
        profilePanel.add(headerPanel, BorderLayout.NORTH);
        profilePanel.add(feedPanel, BorderLayout.CENTER);

        this.add(sidebar, BorderLayout.WEST);
        this.add(profilePanel, BorderLayout.CENTER);
    }

    private static String getProfileTitle(String signedInUsername, String profileUsername) {
        if (signedInUsername.equals(profileUsername)) {
            return "My Profile";
        }
        else {
            return profileUsername + "'s Profile";
        }
    }

    private JPanel createHeaderPanel() {
        UserAccount profileUser = UserStorage.getUser(profileUsername);

        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setBackground(Theme.BACKGROUND);

        Border separator = BorderFactory.createMatteBorder(0, 0, 1, 0, Theme.BACKGROUND3);
        Border padding = BorderFactory.createEmptyBorder(24, 24, 24, 24);
        headerPanel.setBorder(BorderFactory.createCompoundBorder(separator, padding));

        File profilePictureFile = UserStorage.getUserProfilePicture(profileUsername);
        ImageIcon profilePicture = new ImageIcon(profilePictureFile.getPath());
        Image scaledImage = profilePicture.getImage().getScaledInstance(128, 128, Image.SCALE_SMOOTH);
        profilePicture.setImage(scaledImage);

        JLabel profilePictureLabel = new JLabel(profilePicture);
        profilePictureLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel displayNameLabel = new JLabel(profileUser.getDisplayName());
        displayNameLabel.setFont(Theme.BOLD_FONT.deriveFont(24f));
        displayNameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel usernameLabel = new JLabel("@" + profileUsername);
        usernameLabel.setForeground(Theme.TEXT_MUTED);
        usernameLabel.setFont(Theme.MAIN_FONT.deriveFont(16f));
        usernameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        headerPanel.add(profilePictureLabel);
        headerPanel.add(Box.createVerticalStrut(12));
        headerPanel.add(displayNameLabel);
        headerPanel.add(Box.createVerticalStrut(4));
        headerPanel.add(usernameLabel);

        String bio = profileUser.getBio();
        if (!bio.isEmpty()) {
            JTextPane bioPane = createBioPane(bio);

            headerPanel.add(Box.createVerticalStrut(20));
            headerPanel.add(bioPane);
        }

        if (signedInUsername.equals(profileUsername)) {
            JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 0));
            actionPanel.setOpaque(false);

            editProfileButton = new LibertaButton("Edit Profile");
            editProfileButton.setColors(Theme.ACCENT1, Theme.ACCENT2, Theme.TEXT, Theme.TEXT, true);
            editProfileButton.setMargin(new Insets(8, 16, 8, 16));
            editProfileButton.addActionListener(this);

            deleteAccountButton = new LibertaButton("Delete Account");
            deleteAccountButton.setColors(Theme.DANGER, Theme.DANGER.brighter(), Theme.TEXT, Theme.TEXT, true);
            deleteAccountButton.setMargin(new Insets(8, 16, 8, 16));
            deleteAccountButton.addActionListener(this);

            actionPanel.add(editProfileButton);
            actionPanel.add(deleteAccountButton);

            headerPanel.add(Box.createVerticalStrut(16));
            headerPanel.add(actionPanel);
        }

        return headerPanel;
    }

    private static JTextPane createBioPane(String bio) {
        JTextPane bioPane = new JTextPane();
        bioPane.setText(bio);
        bioPane.setEditable(false);
        bioPane.setOpaque(false);
        bioPane.setBorder(null);
        bioPane.setFocusable(false);
        bioPane.setFont(Theme.MAIN_FONT);
        bioPane.setForeground(Theme.TEXT);

        // JTextArea doesn't support center alignment, JLabel doesn't have line wrapping,
        // so we have to use JTextPane with StyledDocument
        // Reference: https://stackoverflow.com/questions/66440929/jtextarea-center-alignment
        StyledDocument documentStyle = bioPane.getStyledDocument();
        SimpleAttributeSet centerAttribute = new SimpleAttributeSet();
        StyleConstants.setAlignment(centerAttribute, StyleConstants.ALIGN_CENTER);
        documentStyle.setParagraphAttributes(0, documentStyle.getLength(), centerAttribute, false);

        // JTextPane needs a fixed width to calculate preferred height for wrapped text, so we have
        // to set its width before getting its preferred size, otherwise it will take more height
        // than necessary and leave a lot of empty space below the bio
        // Reference: https://stackoverflow.com/questions/39455573/how-to-set-fixed-width-but-dynamic-height-on-jtextpane
        bioPane.setSize(new Dimension(600, 400));
        Dimension bioSize = new Dimension(600, bioPane.getPreferredSize().height);
        bioPane.setPreferredSize(bioSize);
        bioPane.setMaximumSize(bioSize);
        bioPane.setAlignmentX(Component.CENTER_ALIGNMENT);

        return bioPane;
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == editProfileButton) {
            // TODO(famiu): open Edit Profile and persist valid changes
        }
        else if (e.getSource() == deleteAccountButton) {
            DeleteAccountDialog deleteAccountDialog = new DeleteAccountDialog(this);
            boolean confirmed = deleteAccountDialog.showDialog();

            if (!confirmed) {
                return;
            }

            UserStorage.deleteUser(signedInUsername);

            LibertaMessageDialog messageDialog = new LibertaMessageDialog(this, "Delete Account",
                    "Account deleted successfully.");
            messageDialog.showDialog();
            switchFrame(new Login());
        }
    }

    // Get the displayed user's posts in reverse chronological order
    private static ArrayList<Post> getProfilePosts(String profileUsername) {
        TreeSet<Integer> userPostIds = UserStorage.getUser(profileUsername).getPostIds();
        ArrayList<Post> profilePosts = new ArrayList<Post>();

        // Convert post IDs to Post objects
        for (int postId : userPostIds) {
            Post post = PostStorage.getPost(postId);
            if (post != null) {
                profilePosts.add(post);
            }
        }

        // Sort posts from newest to oldest using the same approach as getFeedPosts() in Home
        profilePosts.sort((p1, p2) -> p2.getTimestamp().compareTo(p1.getTimestamp()));

        return profilePosts;
    }
}
