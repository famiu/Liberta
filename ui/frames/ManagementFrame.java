package ui.frames;

import java.lang.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import java.awt.*;

import ui.*;
import ui.components.*;
import ui.panels.*;

public abstract class ManagementFrame extends LibertaFrame implements DocumentListener {
    private static final ImageIcon searchIcon = new ImageIcon("./assets/icons/png/32/search-button.png");

    private JTextField searchField;
    private JPanel contentPanel;
    private ManagementListPanel listPanel;

    protected ManagementFrame(String title) {
        super(title, new BorderLayout());
        panel.add(new AdminSidebarPanel(this, "admin", title), BorderLayout.WEST);

        contentPanel = new JPanel(new BorderLayout());

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        searchPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 10, 15));

        JLabel searchLabel = new JLabel("Search:");
        searchLabel.setIcon(searchIcon);
        searchLabel.setFont(Theme.BOLD_FONT);
        searchLabel.setIconTextGap(8);

        searchField = new JTextField();
        searchField.setPreferredSize(new Dimension(350, 40));
        Border outline = BorderFactory.createLineBorder(Theme.BACKGROUND3);
        Border padding = BorderFactory.createEmptyBorder(8, 12, 8, 12);
        searchField.setBorder(BorderFactory.createCompoundBorder(outline, padding));

        searchPanel.add(searchLabel);
        searchPanel.add(searchField);
        contentPanel.add(searchPanel, BorderLayout.NORTH);
        panel.add(contentPanel, BorderLayout.CENTER);
    }

    protected final void setManagementContent(ManagementHeaderPanel header, ManagementListPanel listPanel) {
        this.listPanel = listPanel;
        JScrollPane scrollPane = new JScrollPane(listPanel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(null);
        scrollPane.getViewport().setBackground(Theme.BACKGROUND);
        scrollPane.setColumnHeaderView(header);
        scrollPane.getColumnHeader().setBackground(Theme.BACKGROUND);

        JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();
        verticalScrollBar.setUI(new LibertaScrollBarUI(Theme.TEXT_MUTED, Theme.BACKGROUND2));
        verticalScrollBar.setPreferredSize(new Dimension(18, 0));
        verticalScrollBar.setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6));
        verticalScrollBar.setBackground(Theme.BACKGROUND);

        contentPanel.add(scrollPane, BorderLayout.CENTER);
        searchField.getDocument().addDocumentListener(this);
    }

    private void updateSearch() {
        listPanel.filterRows(searchField.getText().trim());
    }

    public void insertUpdate(DocumentEvent e) {
        updateSearch();
    }

    public void removeUpdate(DocumentEvent e) {
        updateSearch();
    }

    public void changedUpdate(DocumentEvent e) {
        updateSearch();
    }
}
