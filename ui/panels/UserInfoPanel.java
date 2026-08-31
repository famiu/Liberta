package ui.panels;

import entity.*;
import storage.UserStorage;
import ui.Theme;
import ui.components.LibertaButton;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class UserInfoPanel extends JPanel implements ActionListener{
    UserAccount user;
    public UserInfoPanel(UserAccount user){
        this.user = user;
        this.setLayout(new FlowLayout(FlowLayout.CENTER,20,10));

        JLabel username = new JLabel(user.getUsername());
        JLabel displayName = new JLabel(user.getDisplayName());
        JLabel email = new JLabel(user.getEmail());
        JPanel action = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));

        username.setFont(Theme.BOLD_FONT);
        displayName.setFont(Theme.BOLD_FONT);
        email.setFont(Theme.BOLD_FONT);
        username.setSize(new Dimension(200,20));
        username.setPreferredSize(new Dimension(200,20));
        displayName.setSize(new Dimension(200,20));
        displayName.setPreferredSize(new Dimension(200,20));
        email.setSize(new Dimension(300,20));
        email.setPreferredSize(new Dimension(300,20));
        action.setPreferredSize(new Dimension(100,30));
        action.setOpaque(false);

        username.setHorizontalAlignment(SwingConstants.CENTER);
        displayName.setHorizontalAlignment(SwingConstants.CENTER);
        email.setHorizontalAlignment(SwingConstants.CENTER);

        LibertaButton delete = new LibertaButton("Delete");
        delete.setColors(Theme.DANGER, Theme.DANGER.brighter(), Theme.TEXT, Theme.TEXT, false);
        delete.setOpaque(true);
        delete.addActionListener(this);
        action.add(delete);

        this.setPreferredSize(new Dimension(700,50));
        this.setMaximumSize(new Dimension(Integer.MAX_VALUE,50));
        this.add(username);
        this.add(displayName);
        this.add(email);
        this.add(action);
        this.setVisible(true);
    }
    public void actionPerformed(ActionEvent ae){
        UserStorage.deleteUser(user.getUsername());
        Container parent = this.getParent();
        parent.remove(this);
        parent.revalidate();
        parent.repaint();
    }
}
