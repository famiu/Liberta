package ui.panels;

import java.lang.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import entity.*;
import storage.*;

import ui.*;
import ui.components.*;
import ui.dialogs.*;
import ui.frames.*;

public class PostManagementRowPanel extends ManagementRowPanel implements ActionListener{
    private Post post;

    public PostManagementRowPanel(Post post){
        this.post = post;

        JLabel id = new JLabel(Integer.toString(post.getPostId()));
        id.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel author = new JLabel(post.getAuthor());
        JLabel content = new JLabel(post.getContent());

        JPanel action = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        action.setOpaque(false);
        LibertaButton delete = new LibertaButton("Delete");
        delete.setColors(Theme.DANGER, Theme.DANGER.brighter(), Theme.TEXT, Theme.TEXT, false);
        delete.setOpaque(true);
        delete.addActionListener(this);
        action.add(delete);

        this.addLabel(id, 0, 100, 1.0);
        this.addLabel(author, 1, 200, 2.0);
        this.addLabel(content, 2, 300, 3.0);
        this.addCell(action, 3, 100, 30, 1.0);
    }

    public void actionPerformed(ActionEvent ae){
        LibertaFrame parentFrame = this.getParentFrame();
        if (parentFrame != null) {
            DeleteDialog deleteDialog = new DeleteDialog(parentFrame, "Delete Post", 460, 220,
                "Delete post " + post.getPostId() + "?", "This action cannot be undone.", "Delete Post");

            if (!deleteDialog.showDialog()) {
                return;
            }

            PostStorage.deletePost(post.getPostId());
            this.removeRow();
        }
    }
}
