package ui.panels;

import java.lang.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

import ui.*;

public class BrandPanel extends JPanel {
    public BrandPanel() {
        // Need to use GridBagLayout to keep the logo in the middle of the branding panel
        // Reference: https://stackoverflow.com/questions/7223530/how-can-i-properly-center-a-jpanel-fixed-size-inside-a-jframe
        super(new GridBagLayout());

        this.setBackground(Theme.BACKGROUND2);
        Border separator = BorderFactory.createMatteBorder(0, 0, 0, 1, Theme.BACKGROUND3);
        Border padding = BorderFactory.createEmptyBorder(0, 60, 0, 60);
        this.setBorder(BorderFactory.createCompoundBorder(separator, padding));

        ImageIcon logo = new ImageIcon("./assets/branding/png/512/logo-vertical.png");
        Image scaledLogo = logo.getImage().getScaledInstance(340, -1, Image.SCALE_SMOOTH);
        logo.setImage(scaledLogo);
        this.add(new JLabel(logo));
    }
}
