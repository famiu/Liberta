package frames;

import java.lang.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

// This panel is used for form fields in Login and Register frames
public class AuthFieldPanel extends JPanel {
    public AuthFieldPanel(String labelText, JTextField field) {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setOpaque(false);
        this.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel label = new JLabel(labelText);
        label.setFont(Theme.BOLD_FONT);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Allow making the text field smaller when window is resized
        // Also make sure it doesn't get larger than its normal size
        Dimension fieldSize = new Dimension(400, 42);
        field.setMinimumSize(new Dimension(280, 42));
        field.setPreferredSize(fieldSize);
        field.setMaximumSize(fieldSize);

        Border outline = BorderFactory.createLineBorder(Theme.BACKGROUND3);
        Border padding = BorderFactory.createEmptyBorder(8, 12, 8, 12);
        field.setBorder(BorderFactory.createCompoundBorder(outline, padding));
        field.setAlignmentX(Component.LEFT_ALIGNMENT);

        this.add(label);
        this.add(Box.createVerticalStrut(8));
        this.add(field);
    }
}
