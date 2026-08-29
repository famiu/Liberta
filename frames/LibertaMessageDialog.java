package frames;

import java.lang.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LibertaMessageDialog extends LibertaDialog implements ActionListener {
    private LibertaButton okButton;

    public LibertaMessageDialog(LibertaFrame parentFrame, String title, String message) {
        super(parentFrame, title, 440, 180, new BorderLayout(0, 20));

        // Need to use JTextArea instead of JLabel to support multi-line messages and ensure correct wrapping
        LibertaTextArea messageArea = new LibertaTextArea(message);
        messageArea.setEditable(false);
        messageArea.setFocusable(false);
        messageArea.setOpaque(false);
        messageArea.setBorder(null);
        messageArea.setFont(Theme.BOLD_FONT);

        // In order to make JTextArea not take more height than it needs, we need to set a width first
        // Similar to what we did in Profile.java
        // Reference: https://stackoverflow.com/questions/4083322/how-can-i-create-a-jtextarea-with-a-specified-width-and-the-smallest-possible-he
        messageArea.setSize(new Dimension(360, 80));
        Dimension messageSize = new Dimension(360, messageArea.getPreferredSize().height);
        messageArea.setPreferredSize(messageSize);

        // Need to use GridBagLayout to keep the message in the middle without making it fill all the space
        // Reference: https://stackoverflow.com/questions/7223530/how-can-i-properly-center-a-jpanel-fixed-size-inside-a-jframe
        JPanel messagePanel = new JPanel(new GridBagLayout());
        messagePanel.setOpaque(false);
        messagePanel.add(messageArea);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttonPanel.setOpaque(false);

        okButton = new LibertaButton("Ok");
        okButton.setColors(Theme.ACCENT1, Theme.ACCENT2, Theme.TEXT, Theme.TEXT, true);
        okButton.setMargin(new Insets(10, 18, 10, 18));
        okButton.addActionListener(this);
        buttonPanel.add(okButton);

        panel.add(messagePanel, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);
    }

    public void showDialog() {
        this.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == okButton) {
            this.dispose();
        }
    }
}
