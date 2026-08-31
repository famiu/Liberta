package ui.panels;

import java.lang.*;
import javax.swing.*;
import java.awt.*;

import ui.*;

public class ManagementHeaderPanel extends ManagementRowPanel {
    public ManagementHeaderPanel() {
        super();
        this.setBorder(BorderFactory.createEmptyBorder(10, 15, 0, 15));
        // Let the JViewport set the header width so it resizes correctly.
        this.setPreferredSize(new Dimension(0,50));
    }

    public void addHeaderLabel(String text, int column, int width, double weight) {
        JLabel label = this.createHeaderLabel(text);
        this.addLabel(label, column, width, weight);
    }

    public void addHeaderLabel(String text, int column, int width, double weight, int alignment) {
        JLabel label = this.createHeaderLabel(text);
        label.setHorizontalAlignment(alignment);
        this.addLabel(label, column, width, weight);
    }

    private JLabel createHeaderLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(Theme.BOLD_FONT);
        return label;
    }
}
