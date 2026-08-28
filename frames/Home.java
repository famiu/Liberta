package frames;

import java.lang.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import java.time.*;
import entity.*;
import storage.*;

public class Home extends LibertaFrame {
    private FeedPanel feedPanel;
    private SidebarPanel sidebar;

    public Home(String username) {
        super("Home", new BorderLayout());
        sidebar = new SidebarPanel(this, username, "Home");
        feedPanel = new FeedPanel(getFeedPosts(username), username);
        this.add(sidebar, BorderLayout.WEST);
        this.add(feedPanel, BorderLayout.CENTER);
    }

    // Get list of posts in reverse chronological order (most recent first)
    private static ArrayList<Post> getFeedPosts(String username) {
        ArrayList<Post> feedPosts = new ArrayList<Post>(PostStorage.getPosts().values());
        // Remove posts made by the signed-in user
        // Requires using removeIf method of ArrayList with lambda filter
        // Reference: https://stackoverflow.com/questions/13316629/remove-objects-from-an-arraylist-based-on-a-given-criteria
        feedPosts.removeIf((post) -> post.getAuthor().equals(username));
        // Sort posts from newest to oldest
        // Requires using sort method of ArrayList with lambda for sorting, and compareTo method
        // of LocalDateTime for comparing timestamps
        // References:
        // https://stackoverflow.com/questions/2784514/sort-arraylist-of-custom-objects-by-property
        // https://stackoverflow.com/questions/32625407/sorting-localdatetime
        feedPosts.sort((p1, p2) -> p2.getTimestamp().compareTo(p1.getTimestamp()));
        return feedPosts;
    }
}
