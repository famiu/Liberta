package ui.panels;

import java.lang.*;
import javax.swing.*;
import java.awt.*;

import ui.*;
import ui.frames.*;

public abstract class ManagementRowPanel extends JPanel {
    protected ManagementRowPanel() {
        this.setLayout(new GridBagLayout());
        this.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Theme.BACKGROUND3));
        this.setPreferredSize(new Dimension(700,50));
        this.setMaximumSize(new Dimension(Integer.MAX_VALUE,50));
    }

    protected final void addLabel(JLabel label, int column, int width, double weight) {
        this.addCell(label, column, width, 20, weight);
    }

    protected final void addCell(JComponent component, int column, int width, int height, double weight) {
        component.setPreferredSize(new Dimension(width,height));
        // Need zero minimum widths so GridBagLayout can shrink row cells when the window is resized
        // References:
        // https://stackoverflow.com/questions/66476114/shrink-jlabel-inside-a-gridbaglayout-cell
        // https://docs.oracle.com/javase/tutorial/uiswing/layout/gridbag.html
        component.setMinimumSize(new Dimension(0,height));

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = column;
        constraints.gridy = 0;
        constraints.weightx = weight;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(0, 10, 0, 10);
        this.add(component, constraints);
    }

    protected final void removeRow() {
        // Need to use getParent to get the parent panel
        // Reference: https://stackoverflow.com/questions/1938774/jlabel-get-parent-panel
        Container parent = this.getParent();
        parent.remove(this);
        parent.revalidate();
        parent.repaint();
    }

    // Get parent frame of this panel
    // Reference: https://stackoverflow.com/questions/9650874/java-swing-obtain-window-jframe-from-inside-a-jpanel
    protected final LibertaFrame getParentFrame() {
        return (LibertaFrame) SwingUtilities.getWindowAncestor(this);
    }
}
