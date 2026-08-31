package ui.frames;

import javax.swing.*;

import storage.*;

import java.awt.*;

import ui.Theme;
import ui.components.*;
import ui.panels.*;
import ui.dialogs.*;

public class UserManageFrame extends LibertaFrame {
    public UserManageFrame() {
        super("User Management", new BorderLayout());
        panel.add(new AdminSidebarPanel(this, "admin", "User Management"), BorderLayout.WEST);

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        JPanel header = new JPanel(new FlowLayout(FlowLayout.CENTER,20,0));
        JLabel username = new JLabel("Username");
        JLabel displayName = new JLabel("Display Name");
        JLabel email = new JLabel("Email");
        JLabel action = new JLabel("Action");
        username.setFont(Theme.BOLD_FONT);
        displayName.setFont(Theme.BOLD_FONT);
        email.setFont(Theme.BOLD_FONT);
        action.setFont(Theme.BOLD_FONT);
        username.setSize(new Dimension(200,16));
        username.setPreferredSize(new Dimension(200,16));
        displayName.setSize(new Dimension(200,16));
        displayName.setPreferredSize(new Dimension(200,16));
        email.setSize(new Dimension(300,16));
        email.setPreferredSize(new Dimension(300,16));
        action.setSize(new Dimension(100,16));
        action.setPreferredSize(new Dimension(100,16));

        username.setHorizontalAlignment(SwingConstants.CENTER);
        displayName.setHorizontalAlignment(SwingConstants.CENTER);
        email.setHorizontalAlignment(SwingConstants.CENTER);
        action.setHorizontalAlignment(SwingConstants.CENTER);

        header.add(username);
        header.add(displayName);
        header.add(email);
        header.add(action);
        header.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        header.setPreferredSize(new Dimension(700,50));
        header.setMaximumSize(new Dimension(Integer.MAX_VALUE,50));
        contentPanel.add(header);
        JScrollPane scrollPane = new JScrollPane(new UserInfoListPanel());
        scrollPane.setBorder(null);
        JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();
        verticalScrollBar.setUI(new LibertaScrollBarUI(Theme.TEXT_MUTED, Theme.BACKGROUND2));
        verticalScrollBar.setPreferredSize(new Dimension(18, 0));
        verticalScrollBar.setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6));
        verticalScrollBar.setBackground(Theme.BACKGROUND);
        contentPanel.add(scrollPane);

        panel.add(contentPanel, BorderLayout.CENTER);
    }
}