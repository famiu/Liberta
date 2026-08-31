package ui.panels;

import java.lang.*;
import javax.swing.*;

import ui.*;
import ui.components.*;

public abstract class ManagementListPanel extends LibertaScrollablePanel {
    protected ManagementListPanel() {
        super(true);
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBackground(Theme.BACKGROUND);
        this.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        this.setVisible(true);
    }

    public final void filterRows(String query) {
        String search = query.toLowerCase();
        this.removeAll();
        this.addMatchingRows(search);
        // Need to use revalidate() and repaint() to refresh the panel, otherwise the rows will not update correctly.
        this.revalidate();
        this.repaint();
    }

    protected abstract void addMatchingRows(String search);
}
