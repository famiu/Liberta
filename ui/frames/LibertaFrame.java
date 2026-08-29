package ui.frames;

import java.awt.*;
import javax.swing.*;

import ui.components.*;

public abstract class LibertaFrame extends JFrame {
    private static final int INITIAL_WIDTH = 1200;
    private static final int INITIAL_HEIGHT = 800;

    private static final int MIN_WIDTH = 800;
    private static final int MIN_HEIGHT = 600;

    private static final ImageIcon icon = new ImageIcon("./assets/branding/png/512/icon.png");

    protected JPanel panel;
    protected LibertaFrame(String title, LayoutManager layout) {
        super(title);
        this.setSize(INITIAL_WIDTH, INITIAL_HEIGHT);
        this.setMinimumSize(new Dimension(MIN_WIDTH, MIN_HEIGHT));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setIconImage(icon.getImage());
        this.setLocationRelativeTo(null);

        panel = new JPanel();
        panel.setLayout(layout);
        this.add(panel);
    }

    public void switchFrame(LibertaFrame frame) {
        frame.setBounds(this.getBounds());
        frame.setVisible(true);
        this.dispose();
    }

    // Press button by default when Enter is pressed
    // Reference: https://stackoverflow.com/questions/13731710/allowing-the-enter-key-to-press-the-submit-button-as-opposed-to-only-using-mo
    protected void setDefaultButton(LibertaButton button) {
        this.getRootPane().setDefaultButton(button);
    }
}
