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

public class UserManagementRowPanel extends ManagementRowPanel implements ActionListener{
    private UserAccount user;

    public UserManagementRowPanel(UserAccount user){
        this.user = user;

        JLabel username = new JLabel(user.getUsername());
        JLabel displayName = new JLabel(user.getDisplayName());
        JLabel email = new JLabel(user.getEmail());

        JPanel action = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        action.setOpaque(false);
        LibertaButton delete = new LibertaButton("Delete");
        delete.setColors(Theme.DANGER, Theme.DANGER.brighter(), Theme.TEXT, Theme.TEXT, false);
        delete.setOpaque(true);
        delete.addActionListener(this);
        action.add(delete);

        this.addLabel(username, 0, 200, 2.0);
        this.addLabel(displayName, 1, 200, 2.0);
        this.addLabel(email, 2, 300, 3.0);
        this.addCell(action, 3, 100, 30, 1.0);
    }

    public void actionPerformed(ActionEvent ae){
        LibertaFrame parentFrame = this.getParentFrame();
        if (parentFrame != null) {
            String message = "Their profile, posts, and likes will be permanently deleted.\n"
                + "This action cannot be undone.";
            DeleteDialog deleteDialog = new DeleteDialog(parentFrame, "Delete User", 540, 240,
                "Delete @" + user.getUsername() + "?", message, "Delete User");

            if (!deleteDialog.showDialog()) {
                return;
            }

            UserStorage.deleteUser(user.getUsername());
            this.removeRow();
        }
    }
}
