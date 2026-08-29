package frames;

import java.lang.*;
import java.io.*;
import java.time.*;
import java.time.format.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.*;

import entity.*;
import storage.*;

public class PostPanel extends JPanel implements ActionListener {
    private static final ImageIcon likeIcon = new ImageIcon("./assets/icons/png/24/like-button.png");
    private static final ImageIcon likedIcon = new ImageIcon("./assets/icons/png/24/liked-button.png");

    private Post post;
    private String signedInUser;

    private LibertaButton profilePictureButton;
    private LibertaButton displayNameButton;
    private LibertaButton usernameButton;
    private LibertaButton likeButton;
    private LibertaButton deleteButton;

    public PostPanel(Post post, String signedInUser) {
        this.post = post;
        this.signedInUser = signedInUser;

        String author = this.post.getAuthor();
        String displayName = UserStorage.getUser(author).getDisplayName();
        String postContent = this.post.getContent();
        String timeAgo = getTimeAgo(this.post.getTimestamp());

        File profilePictureFile = UserStorage.getUserProfilePicture(author);
        ImageIcon profilePicture = new ImageIcon(profilePictureFile.getPath());
        Image scaledImage = profilePicture.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        profilePicture.setImage(scaledImage);

        Border padding = BorderFactory.createEmptyBorder(12, 15, 12, 15);

        this.setLayout(new BorderLayout(15, 0));
        this.setBorder(padding);
        this.setBackground(Theme.BACKGROUND);

        profilePictureButton = new LibertaButton(profilePicture);
        profilePictureButton.setColors(Theme.BACKGROUND, Theme.BACKGROUND2, Theme.TEXT, Theme.TEXT, true);
        profilePictureButton.setBorder(BorderFactory.createEmptyBorder());
        profilePictureButton.setVerticalAlignment(SwingConstants.TOP);
        profilePictureButton.addActionListener(this);

        JPanel profilePicturePanel = new JPanel(new BorderLayout());
        profilePicturePanel.setOpaque(false);
        profilePicturePanel.add(profilePictureButton, BorderLayout.NORTH);

        JPanel bodyPanel = new JPanel(new BorderLayout());
        JPanel headerPanel = new JPanel(new BorderLayout());
        bodyPanel.setOpaque(false);
        headerPanel.setOpaque(false);

        FlowLayout authorLayout = new FlowLayout(FlowLayout.LEFT, 0, 0);
        // Make sure the display name and username are aligned correctly
        authorLayout.setAlignOnBaseline(true);
        JPanel authorPanel = new JPanel(authorLayout);
        authorPanel.setOpaque(false);

        displayNameButton = new LibertaButton(displayName);
        displayNameButton.setBorder(BorderFactory.createEmptyBorder());
        displayNameButton.addActionListener(this);

        usernameButton = new LibertaButton("@" + author);
        usernameButton.setColors(Theme.BACKGROUND, Theme.BACKGROUND, Theme.TEXT_MUTED, Theme.ACCENT1, false);
        usernameButton.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));
        usernameButton.setFont(Theme.MAIN_FONT);
        usernameButton.addActionListener(this);

        authorPanel.add(displayNameButton);
        authorPanel.add(usernameButton);

        JLabel timeLabel = new JLabel(timeAgo);
        timeLabel.setForeground(Theme.TEXT_MUTED);

        headerPanel.add(authorPanel, BorderLayout.WEST);
        headerPanel.add(timeLabel, BorderLayout.EAST);

        // Can't set post content because it would make caret update and scroll the frame
        LibertaTextArea contentArea = new LibertaTextArea();
        // Disable caret update to prevent text area from scrolling the frame
        // Reference: https://stackoverflow.com/questions/3972337/java-swing-jtextarea-in-a-jscrollpane-how-to-prevent-auto-scroll
        DefaultCaret caret = (DefaultCaret) contentArea.getCaret();
        caret.setUpdatePolicy(DefaultCaret.NEVER_UPDATE);
        // Set the text now since caret update is disabled
        contentArea.setText(postContent);
        contentArea.setDisplayOnly();

        bodyPanel.add(headerPanel, BorderLayout.NORTH);
        bodyPanel.add(contentArea, BorderLayout.CENTER);

        JPanel actionPanel = new JPanel();
        actionPanel.setLayout(new BoxLayout(actionPanel, BoxLayout.X_AXIS));
        actionPanel.setOpaque(false);
        actionPanel.setBorder(BorderFactory.createEmptyBorder(8, 0, 0, 0));

        likeButton = new LibertaButton();
        likeButton.setBorder(BorderFactory.createEmptyBorder());
        likeButton.addActionListener(this);
        updateLikeButton();
        actionPanel.add(likeButton);

        if (this.signedInUser.equals(author)) {
            ImageIcon deleteIcon = new ImageIcon("./assets/icons/png/24/delete-button.png");
            deleteButton = new LibertaButton(deleteIcon);
            deleteButton.setBorder(BorderFactory.createEmptyBorder());
            deleteButton.addActionListener(this);
            actionPanel.add(Box.createHorizontalGlue());
            actionPanel.add(deleteButton);
        }

        bodyPanel.add(actionPanel, BorderLayout.SOUTH);

        this.add(profilePicturePanel, BorderLayout.WEST);
        this.add(bodyPanel, BorderLayout.CENTER);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == likeButton) {
            post.toggleLike(signedInUser);
            PostStorage.updatePost(post);
            updateLikeButton();
        }
        else if (e.getSource() == deleteButton) {
            LibertaFrame parentFrame = getParentFrame();
            if (parentFrame != null) {
                DeletePostDialog deletePostDialog = new DeletePostDialog(parentFrame);
                boolean confirmed = deletePostDialog.showDialog();

                if (!confirmed) {
                    return;
                }

                PostStorage.deletePost(this.post.getPostId());
                LibertaMessageDialog messageDialog = new LibertaMessageDialog(parentFrame, "Delete Post",
                        "Post deleted successfully.");
                messageDialog.showDialog();
                parentFrame.switchFrame(new Profile(this.signedInUser));
            }
        }
        else if (e.getSource() == profilePictureButton || e.getSource() == displayNameButton
                || e.getSource() == usernameButton) {
            LibertaFrame parentFrame = getParentFrame();
            if (parentFrame != null) {
                parentFrame.switchFrame(new Profile(this.signedInUser, this.post.getAuthor()));
            }
        }
    }

    private void updateLikeButton() {
        if (post.isLikedBy(signedInUser)) {
            likeButton.setIcon(likedIcon);
        }
        else {
            likeButton.setIcon(likeIcon);
        }
        likeButton.setText("" + post.getLikeCount());
    }

    private LibertaFrame getParentFrame() {
        Window parentWindow = SwingUtilities.getWindowAncestor(this);
        if (parentWindow instanceof LibertaFrame) {
            return (LibertaFrame) parentWindow;
        }
        return null;
    }

    // Formats post time nicely
    private static String getTimeAgo(LocalDateTime postTime) {
        LocalDateTime now = LocalDateTime.now();
        LocalDate postDate = postTime.toLocalDate();
        LocalDate today = now.toLocalDate();

        if (postDate.equals(today)) {
            // Calculate how much time has passed since the post was created
            // Reference: https://stackoverflow.com/questions/3859288/how-to-calculate-time-ago-in-java
            long minutes = Duration.between(postTime, now).toMinutes();
            long hours = Duration.between(postTime, now).toHours();

            if (minutes < 1) {
                return "Just now";
            }
            else if (minutes == 1) {
                return "1 minute ago";
            }
            else if (minutes < 60) {
                return minutes + " minutes ago";
            }
            else if (hours == 1) {
                return "1 hour ago";
            }
            else {
                return hours + " hours ago";
            }
        }
        // Compare with the previous date to identify posts from yesterday
        // Reference: https://stackoverflow.com/questions/67918780/check-if-date-is-before-today-regardless-hour
        else if (postDate.equals(today.minusDays(1))) {
            // Format older post times with custom date and time patterns
            // References:
            // https://stackoverflow.com/questions/22463062/how-can-i-parse-format-dates-with-localdatetime-java-8
            // https://docs.oracle.com/javase/8/docs/api/java/time/format/DateTimeFormatter.html#patterns
            return "Yesterday at " + postTime.format(DateTimeFormatter.ofPattern("h:mm a"));
        }
        // Use the same approach as above to identify posts from the last 7 days
        else if (postDate.isAfter(today.minusDays(7))) {
            return postTime.format(DateTimeFormatter.ofPattern("EEEE 'at' h:mm a"));
        }
        else if (postDate.getYear() == today.getYear()) {
            return postTime.format(DateTimeFormatter.ofPattern("MMM d"));
        }
        else {
            return postTime.format(DateTimeFormatter.ofPattern("MMM d, yyyy"));
        }
    }
}
