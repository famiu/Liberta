package ui.panels;

import entity.*;
import storage.*;
import ui.Theme;
import ui.components.LibertaButton;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class PostInfoPanel extends JPanel implements ActionListener{
    Post post;
    public PostInfoPanel(Post post){
        this.post = post;
        this.setLayout(new FlowLayout(FlowLayout.CENTER,20,10));

        JLabel id = new JLabel(Integer.toString(post.getPostId()));
        JLabel author = new JLabel(post.getAuthor());
        JLabel content = new JLabel(post.getContent());
        JPanel action = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));

        id.setFont(Theme.BOLD_FONT);
        author.setFont(Theme.BOLD_FONT);
        content.setFont(Theme.BOLD_FONT);
        id.setSize(new Dimension(100,20));
        id.setPreferredSize(new Dimension(100,20));
        author.setSize(new Dimension(200,20));
        author.setPreferredSize(new Dimension(200,20));
        content.setSize(new Dimension(300,20));
        content.setPreferredSize(new Dimension(300,20));
        action.setPreferredSize(new Dimension(100,30));
        action.setOpaque(false);

        id.setHorizontalAlignment(SwingConstants.CENTER);
        author.setHorizontalAlignment(SwingConstants.CENTER);
        content.setHorizontalAlignment(SwingConstants.CENTER);

        LibertaButton delete = new LibertaButton("Delete");
        delete.setColors(Theme.DANGER, Theme.DANGER.brighter(), Theme.TEXT, Theme.TEXT, false);
        delete.setOpaque(true);
        delete.addActionListener(this);
        action.add(delete);

        this.setPreferredSize(new Dimension(700,50));
        this.setMaximumSize(new Dimension(Integer.MAX_VALUE,50));
        this.add(id);
        this.add(author);
        this.add(content);
        this.add(action);
        this.setVisible(true);
    }
    public void actionPerformed(ActionEvent ae){
        PostStorage.deletePost(post.getPostId());
        Container parent = this.getParent();
        parent.remove(this);
        parent.revalidate();
        parent.repaint();
    }
}
