package frames;

import java.lang.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LibertaMessageDialog extends LibertaDialog implements ActionListener {
    private LibertaButton okButton;

    public LibertaMessageDialog(LibertaFrame parentFrame, String title, String message) {
        super(parentFrame, title, 440, 180, new BorderLayout(0, 20));

        JLabel messageLabel = new JLabel(message, SwingConstants.CENTER);
        messageLabel.setFont(Theme.BOLD_FONT);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttonPanel.setOpaque(false);

        okButton = new LibertaButton("Ok");
        okButton.setColors(Theme.ACCENT1, Theme.ACCENT2, Theme.TEXT, Theme.TEXT, true);
        okButton.setMargin(new Insets(10, 18, 10, 18));
        okButton.addActionListener(this);
        buttonPanel.add(okButton);

        panel.add(messageLabel, BorderLayout.CENTER);
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
