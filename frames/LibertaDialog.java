package frames;

import java.lang.*;
import javax.swing.*;
import java.awt.*;

public abstract class LibertaDialog extends JDialog {
    protected JPanel panel;

    // Constructor is protected since the class is abstract and only child classes should call it
    protected LibertaDialog(LibertaFrame parentFrame, String title, int width, int height, LayoutManager layout) {
        super(parentFrame, title, true);

        this.setSize(width, height);
        this.setResizable(false);
        // Make sure dialog is cleaned after closing and doesn't leak memory
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(parentFrame);

        panel = new JPanel(layout);
        panel.setBackground(Theme.BACKGROUND);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        this.add(panel);
    }
}
