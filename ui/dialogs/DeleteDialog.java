package ui.dialogs;

import java.lang.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import ui.*;
import ui.components.*;
import ui.frames.*;

public abstract class DeleteDialog extends LibertaDialog implements ActionListener {
    private JLabel headingLabel;
    private LibertaTextArea messageArea;
    private LibertaButton cancelButton;
    private LibertaButton deleteButton;
    private boolean confirmed;

    protected DeleteDialog(LibertaFrame parentFrame, String title, int width, int height) {
        super(parentFrame, title, width, height, new BorderLayout(0, 20));

        ImageIcon deleteIcon = new ImageIcon("./assets/icons/png/48/delete-button.png");
        JLabel deleteIconLabel = new JLabel(deleteIcon);
        deleteIconLabel.setVerticalAlignment(SwingConstants.TOP);

        headingLabel = new JLabel();
        headingLabel.setFont(Theme.BOLD_FONT);
        headingLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        messageArea = new LibertaTextArea();
        messageArea.setDisplayOnly();
        messageArea.setForeground(Theme.TEXT_MUTED);
        messageArea.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setOpaque(false);
        textPanel.add(headingLabel);
        textPanel.add(Box.createVerticalStrut(8));
        textPanel.add(messageArea);

        JPanel messagePanel = new JPanel(new BorderLayout(15, 0));
        messagePanel.setOpaque(false);
        messagePanel.add(deleteIconLabel, BorderLayout.WEST);
        messagePanel.add(textPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonPanel.setOpaque(false);

        cancelButton = new LibertaButton("Cancel");
        cancelButton.setColors(Theme.BACKGROUND2, Theme.BACKGROUND3, Theme.TEXT_MUTED, Theme.TEXT, true);
        cancelButton.setMargin(new Insets(10, 18, 10, 18));
        cancelButton.addActionListener(this);
        this.setDefaultButton(cancelButton);

        deleteButton = new LibertaButton();
        deleteButton.setColors(Theme.DANGER, Theme.DANGER.brighter(), Theme.TEXT, Theme.TEXT, true);
        deleteButton.setMargin(new Insets(10, 18, 10, 18));
        deleteButton.addActionListener(this);

        buttonPanel.add(cancelButton);
        buttonPanel.add(deleteButton);

        panel.add(messagePanel, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);
    }

    protected void setDeleteButtonText(String text) {
        deleteButton.setText(text);
    }

    protected void setMessage(String heading, String message) {
        headingLabel.setText(heading);
        messageArea.setText(message);
    }

    public boolean showDialog() {
        confirmed = false;
        this.setVisible(true);
        return confirmed;
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == cancelButton) {
            this.dispose();
        }
        else if (e.getSource() == deleteButton) {
            confirmed = true;
            this.dispose();
        }
    }
}
