package frames;

import java.awt.*;
import javax.swing.*;

public abstract class LibertaFrame extends JFrame {
    private static final int INITIAL_WIDTH = 1280;
    private static final int INITIAL_HEIGHT = 720;

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

    protected void switchFrame(LibertaFrame frame) {
        frame.setBounds(this.getBounds());
        frame.setVisible(true);
        this.dispose();
    }
}
