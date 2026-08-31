package ui.panels;

import entity.*;
import storage.*;
import ui.Theme;

import javax.swing.*;
import java.awt.*;

public class PostInfoListPanel extends ScrollablePanel{
    public PostInfoListPanel(){
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBackground(Theme.BACKGROUND);
        this.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        for(Post post: PostStorage.getPosts().values()){
            this.add(new PostInfoPanel(post));
            
            JPanel separator = new JPanel();
            separator.setBackground(Theme.BACKGROUND3);
            separator.setMinimumSize(new Dimension(Integer.MAX_VALUE, 2));
            separator.setPreferredSize(new Dimension(Integer.MAX_VALUE, 2));
            separator.setMaximumSize(new Dimension(Integer.MAX_VALUE, 2));
            separator.setAlignmentX(Component.LEFT_ALIGNMENT);
            this.add(separator);
        }
        this.setVisible(true);
    }
}
