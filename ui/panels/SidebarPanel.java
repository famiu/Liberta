package ui.panels;

import java.lang.*;
import java.io.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

import entity.*;
import storage.*;

import ui.*;
import ui.components.*;
import ui.dialogs.*;
import ui.frames.*;

public class SidebarPanel extends JPanel  {
    protected static final ImageIcon logoutIcon = new ImageIcon("./assets/icons/png/32/logout-button.png");

    protected LibertaFrame parentFrame;
    protected String username;

    protected LibertaButton logoutButton;

    public SidebarPanel(LibertaFrame parentFrame, String username, String activeItem) {
        this.parentFrame = parentFrame;
        this.username = username;

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBackground(Theme.BACKGROUND_DARK);
        Border padding = BorderFactory.createEmptyBorder(15, 15, 15, 15);
        Border separator = BorderFactory.createMatteBorder(0, 0, 0, 1, Theme.BACKGROUND3);
        this.setBorder(BorderFactory.createCompoundBorder(separator, padding));

    }

    protected static JPanel createNavigationPanel(LibertaButton button, boolean active) {
        Dimension indicatorSize = new Dimension(3, 28);
        JPanel indicator = new JPanel();
        indicator.setOpaque(active);
        if (active) {
            indicator.setBackground(Theme.ACCENT1);
        }
        indicator.setMinimumSize(indicatorSize);
        indicator.setPreferredSize(indicatorSize);
        indicator.setMaximumSize(indicatorSize);
        indicator.setAlignmentY(Component.CENTER_ALIGNMENT);

        JPanel navigationPanel = new JPanel();
        navigationPanel.setLayout(new BoxLayout(navigationPanel, BoxLayout.X_AXIS));
        navigationPanel.setOpaque(false);
        navigationPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        button.setAlignmentY(Component.CENTER_ALIGNMENT);
        navigationPanel.add(indicator);
        navigationPanel.add(Box.createHorizontalStrut(8));
        navigationPanel.add(button);
        navigationPanel.setMaximumSize(navigationPanel.getPreferredSize());
        return navigationPanel;
    }

    
}
