package ui.frames;

import javax.swing.*;

import storage.*;

import java.awt.*;

import ui.Theme;
import ui.components.*;
import ui.panels.*;
import ui.dialogs.*;

public class PostManageFrame extends LibertaFrame {
    public PostManageFrame() {
        super("Post Management", new BorderLayout());
        panel.add(new AdminSidebarPanel(this, "admin", "Post Management"), BorderLayout.WEST);

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        JPanel header = new JPanel(new FlowLayout(FlowLayout.CENTER,20,0));
        JLabel id = new JLabel("Id");
        JLabel author = new JLabel("Author");
        JLabel content = new JLabel("Content");
        JLabel action = new JLabel("Action");
        id.setFont(Theme.BOLD_FONT);
        author.setFont(Theme.BOLD_FONT);
        content.setFont(Theme.BOLD_FONT);
        action.setFont(Theme.BOLD_FONT);
        id.setSize(new Dimension(100,16));
        id.setPreferredSize(new Dimension(100,16));
        author.setSize(new Dimension(200,16));
        author.setPreferredSize(new Dimension(200,16));
        content.setSize(new Dimension(300,16));
        content.setPreferredSize(new Dimension(300,16));
        action.setSize(new Dimension(100,16));
        action.setPreferredSize(new Dimension(100,16));

        id.setHorizontalAlignment(SwingConstants.CENTER);
        author.setHorizontalAlignment(SwingConstants.CENTER);
        content.setHorizontalAlignment(SwingConstants.CENTER);
        action.setHorizontalAlignment(SwingConstants.CENTER);

        header.add(id);
        header.add(author);
        header.add(content);
        header.add(action);
        header.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        header.setPreferredSize(new Dimension(700,50));
        header.setMaximumSize(new Dimension(Integer.MAX_VALUE,50));
        contentPanel.add(header);
        JScrollPane scrollPane = new JScrollPane(new PostInfoListPanel());
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