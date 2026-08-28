package frames;

import java.lang.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;

import entity.*;
import storage.*;

public class Profile extends LibertaFrame {
    private String signedInUsername;
    private String profileUsername;

    private SidebarPanel sidebar;
    private FeedPanel feedPanel;

    public Profile(String signedInUsername) {
        this(signedInUsername, signedInUsername);
    }

    public Profile(String signedInUsername, String profileUsername) {
        super(signedInUsername.equals(profileUsername) ? "My Profile" : profileUsername + "'s Profile", new BorderLayout());

        this.signedInUsername = signedInUsername;
        this.profileUsername = profileUsername;

        String activeItem;
        if (signedInUsername.equals(profileUsername)) {
            activeItem = "My Profile";
        }
        else {
            activeItem = "Home";
        }

        // TODO(famiu): add the profile header, bio, and remaining actions after like and bio
        // storage support is implemented.
        sidebar = new SidebarPanel(this, signedInUsername, activeItem);
        feedPanel = new FeedPanel(getProfilePosts(profileUsername), signedInUsername);

        this.add(sidebar, BorderLayout.WEST);
        this.add(feedPanel, BorderLayout.CENTER);
    }

    // Get the displayed user's posts in reverse chronological order
    private static ArrayList<Post> getProfilePosts(String profileUsername) {
        TreeSet<Integer> userPostIds = UserStorage.getUser(profileUsername).getPostIds();
        ArrayList<Post> profilePosts = new ArrayList<Post>();

        // Convert post IDs to Post objects
        for (Integer postId : userPostIds) {
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
