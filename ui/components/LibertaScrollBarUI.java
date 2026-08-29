package ui.components;

import java.lang.*;
import javax.swing.*;
import javax.swing.plaf.basic.*;
import java.awt.*;

// Swing default scrollbar looks ugly, UIManager doesn't work for some reason, so we need to
// override BasicScrollBarUI
// References:
// https://stackoverflow.com/questions/11074172/change-background-color-of-scrollbar-end-buttons
// https://docs.oracle.com/en/java/javase/26/docs/api/java.desktop/javax/swing/plaf/basic/BasicScrollBarUI.html
public class LibertaScrollBarUI extends BasicScrollBarUI {
    private Color customThumbColor;
    private Color customTrackColor;

    public LibertaScrollBarUI(Color thumbColor, Color trackColor) {
        super();
        this.customThumbColor = thumbColor;
        this.customTrackColor = trackColor;
    }

    protected void configureScrollBarColors() {
        this.trackColor = this.customTrackColor;
        this.trackHighlightColor = this.customTrackColor;
        this.thumbColor = this.customThumbColor;
        this.thumbHighlightColor = this.customThumbColor;
        this.thumbLightShadowColor = this.customThumbColor;
        this.thumbDarkShadowColor = this.customThumbColor;
    }

    // BasicScrollBarUI has no way to hide the arrow buttons, the only apparent way to hide them is
    // to make them zero-sized
    // Reference: https://stackoverflow.com/questions/1786886/remove-arrows-from-swing-scrollbar-in-jscrollpane
    protected JButton createDecreaseButton(int orientation) {
        return createInvisibleButton();
    }

    protected JButton createIncreaseButton(int orientation) {
        return createInvisibleButton();
    }

    // Makes an invisible button with zero size
    // Used to make increase/decrease buttons invisible, because they look ugly
    private static JButton createInvisibleButton() {
        JButton button = new JButton();
        Dimension size = new Dimension(0, 0);
        button.setMinimumSize(size);
        button.setPreferredSize(size);
        button.setMaximumSize(size);
        button.setFocusable(false);
        return button;
    }
}
