package ui.dialogs;

import java.lang.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;

import ui.*;
import ui.components.*;
import ui.frames.*;

public class CreatePostDialog extends LibertaDialog implements ActionListener, DocumentListener {
    private static final int POST_CHARACTER_LIMIT = 280;

    private LibertaTextArea contentArea;
    private JLabel characterCountLabel;
    private LibertaButton cancelButton;
    private LibertaButton createPostButton;
    private String postContent;

    public CreatePostDialog(LibertaFrame parentFrame) {
        super(parentFrame, "Create Post", 520, 340, new BorderLayout(0, 15));

        contentArea = new LibertaTextArea();
        contentArea.setBackground(Theme.BACKGROUND2);
        contentArea.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        // Update the character count when the text changes
        // Needs to use DocumentListener to detect changes in the text area
        // Reference: https://stackoverflow.com/questions/8833700/display-the-number-of-characters-in-jtextarea
        contentArea.getDocument().addDocumentListener(this);

        JScrollPane scrollPane = new JScrollPane(contentArea);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(BorderFactory.createLineBorder(Theme.BACKGROUND3));

        JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();
        verticalScrollBar.setUI(new LibertaScrollBarUI(Theme.TEXT_MUTED, Theme.BACKGROUND3));
        verticalScrollBar.setPreferredSize(new Dimension(14, 0));
        verticalScrollBar.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
        verticalScrollBar.setBackground(Theme.BACKGROUND2);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonPanel.setOpaque(false);

        cancelButton = new LibertaButton("Cancel");
        cancelButton.setColors(Theme.BACKGROUND2, Theme.BACKGROUND3, Theme.TEXT_MUTED, Theme.TEXT, true);
        cancelButton.setMargin(new Insets(10, 18, 10, 18));
        cancelButton.addActionListener(this);

        createPostButton = new LibertaButton("Create Post");
        createPostButton.setColors(Theme.ACCENT1, Theme.ACCENT2, Theme.TEXT, Theme.TEXT, true);
        createPostButton.setMargin(new Insets(10, 18, 10, 18));
        createPostButton.addActionListener(this);
        createPostButton.setEnabled(false);
        this.setDefaultButton(createPostButton);

        buttonPanel.add(cancelButton);
        buttonPanel.add(createPostButton);

        characterCountLabel = new JLabel("0/" + POST_CHARACTER_LIMIT);
        characterCountLabel.setForeground(Theme.TEXT_MUTED);

        JPanel footerPanel = new JPanel(new BorderLayout());
        footerPanel.setOpaque(false);
        footerPanel.add(characterCountLabel, BorderLayout.WEST);
        footerPanel.add(buttonPanel, BorderLayout.EAST);

        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(footerPanel, BorderLayout.SOUTH);
    }

    public String showDialog() {
        postContent = null;
        contentArea.setText("");
        this.setVisible(true);
        return postContent;
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == cancelButton) {
            this.dispose();
        }
        else if (e.getSource() == createPostButton) {
            postContent = contentArea.getText().trim();
            this.dispose();
        }
    }

    private void updateCharacterCount() {
        String content = contentArea.getText();
        int characterCount = content.length();
        characterCountLabel.setText(characterCount + "/" + POST_CHARACTER_LIMIT);
        if (characterCount > POST_CHARACTER_LIMIT) {
            characterCountLabel.setForeground(Theme.DANGER);
        }
        else {
            characterCountLabel.setForeground(Theme.TEXT_MUTED);
        }
        createPostButton.setEnabled(!content.trim().isEmpty() && characterCount <= POST_CHARACTER_LIMIT);
    }

    public void insertUpdate(DocumentEvent e) {
        updateCharacterCount();
    }

    public void removeUpdate(DocumentEvent e) {
        updateCharacterCount();
    }

    public void changedUpdate(DocumentEvent e) {
        updateCharacterCount();
    }
}
