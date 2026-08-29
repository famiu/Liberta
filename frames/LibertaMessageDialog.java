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
        messageArea.setDisplayOnly();
        messageArea.setFont(Theme.BOLD_FONT);

        // Make sure the text area for short messages doesn't take up more space than it needs,
        // otherwise it doesn't center properly
        // Need to disable line wrap temporarily before measuring to get the correct width
        // Reference: https://stackoverflow.com/questions/33453081/swing-jtextarea-fit-text
        messageArea.setLineWrap(false);
        // Make sure the message area doesn't get larger than the maximum width
        int messageWidth = Math.min(360, messageArea.getPreferredSize().width);
        messageArea.setLineWrap(true);

        // Need to set a width before getting the preferred height so wrapped text uses the correct height
        // Reference: https://stackoverflow.com/questions/4083322/how-can-i-create-a-jtextarea-with-a-specified-width-and-the-smallest-possible-he
        messageArea.setSize(new Dimension(messageWidth, 80));
        Dimension messageSize = new Dimension(messageWidth, messageArea.getPreferredSize().height);
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
        this.setDefaultButton(okButton);
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
