package ui.panels;

import entity.*;
import storage.*;
import ui.Theme;

import javax.swing.*;
import java.awt.*;

public class UserInfoListPanel extends ScrollablePanel{
    public UserInfoListPanel(){
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBackground(Theme.BACKGROUND);
        this.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        for(UserAccount user: UserStorage.getAllUser().values()){
            this.add(new UserInfoPanel(user));
            
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
