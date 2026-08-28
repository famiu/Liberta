package frames;

import java.lang.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class DeletePostDialog extends LibertaDialog implements ActionListener {
    private LibertaButton cancelButton;
    private LibertaButton deleteButton;
    private boolean confirmed;

    public DeletePostDialog(LibertaFrame parentFrame) {
        super(parentFrame, "Delete Post", 460, 220, new BorderLayout(0, 20));

        ImageIcon deleteIcon = new ImageIcon("./assets/icons/png/48/delete-button.png");
        JLabel deleteIconLabel = new JLabel(deleteIcon);
        deleteIconLabel.setVerticalAlignment(SwingConstants.TOP);

        JLabel headingLabel = new JLabel("Delete this post?");
        headingLabel.setFont(Theme.BOLD_FONT);

        JLabel warningLabel = new JLabel("This action cannot be undone.");
        warningLabel.setForeground(Theme.TEXT_MUTED);

        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setOpaque(false);
        headingLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        warningLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        textPanel.add(headingLabel);
        textPanel.add(Box.createVerticalStrut(8));
        textPanel.add(warningLabel);

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

        deleteButton = new LibertaButton("Delete Post");
        deleteButton.setColors(Theme.ACCENT1, Theme.ACCENT2, Theme.TEXT, Theme.TEXT, true);
        deleteButton.setMargin(new Insets(10, 18, 10, 18));
        deleteButton.addActionListener(this);

        buttonPanel.add(cancelButton);
        buttonPanel.add(deleteButton);

        panel.add(messagePanel, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);
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
