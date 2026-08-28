package frames;

import java.lang.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;

import entity.*;

public class FeedPanel extends JPanel {
    public FeedPanel(ArrayList<Post> posts, String username) {
        this.setLayout(new BorderLayout());
        this.setBackground(Theme.BACKGROUND);

        PostListPanel postListPanel = new PostListPanel(posts, username);
        JScrollPane scrollPane = new JScrollPane(postListPanel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(null);
        // scrollPane.setBackground() doesn't work for some reason, need to use getViewport()
        // Reference: https://coderanch.com/t/338507/java/JScrollPane-background-color
        scrollPane.getViewport().setBackground(Theme.BACKGROUND);

        JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();
        verticalScrollBar.setUI(new LibertaScrollBarUI(Theme.TEXT_MUTED, Theme.BACKGROUND2));
        verticalScrollBar.setPreferredSize(new Dimension(18, 0));
        verticalScrollBar.setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6));
        verticalScrollBar.setBackground(Theme.BACKGROUND);

        this.add(scrollPane, BorderLayout.CENTER);
    }
}
