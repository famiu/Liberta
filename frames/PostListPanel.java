package frames;

import java.lang.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;

import entity.*;

public class PostListPanel extends ScrollablePanel {
    public PostListPanel(ArrayList<Post> posts, String username) {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBackground(Theme.BACKGROUND);
        this.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        for (int i = 0; i < posts.size(); i++) {
            PostPanel postPanel = new PostPanel(posts.get(i), username);
            postPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
            this.add(postPanel);

            // Add a separator after each post except the last one
            // I avoided JSeparator because it looks ugly for some reason
            if (i < posts.size() - 1) {
                JPanel separator = new JPanel();
                separator.setBackground(Theme.BACKGROUND3);
                separator.setMinimumSize(new Dimension(0, 1));
                separator.setPreferredSize(new Dimension(0, 1));
                separator.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
                separator.setAlignmentX(Component.LEFT_ALIGNMENT);
                this.add(separator);
            }
        }
    }

}
