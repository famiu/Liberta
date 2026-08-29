package ui.components;

import java.lang.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import ui.*;

public class LibertaButton extends JButton implements MouseListener {
    private Color normalBackground, hoverBackground, normalForeground, hoverForeground;

    public LibertaButton() {
        super();
        this.initialize();
    }

    public LibertaButton(String text) {
        super(text);
        this.initialize();
    }

    public LibertaButton(ImageIcon icon) {
        super(icon);
        this.initialize();
    }

    public LibertaButton(String text, ImageIcon icon) {
        super(text, icon);
        this.initialize();
    }

    private void initialize() {
        this.setBorderPainted(false);
        this.setFocusPainted(false);
        this.setIconTextGap(10);
        this.setFont(Theme.BOLD_FONT);
        // Make hand shape appear when hovering over the button
        // Reference: https://stackoverflow.com/questions/27194858/jbutton-default-cursor
        this.setCursor(new Cursor(Cursor.HAND_CURSOR));
        this.setColors(Theme.BACKGROUND, Theme.BACKGROUND, Theme.TEXT, Theme.ACCENT1, false);
        this.addMouseListener(this);
    }

    public void setColors(Color normalBackground, Color hoverBackground, Color normalForeground,
            Color hoverForeground, boolean filled) {
        this.normalBackground = normalBackground;
        this.hoverBackground = hoverBackground;
        this.normalForeground = normalForeground;
        this.hoverForeground = hoverForeground;
        this.setContentAreaFilled(filled);
        if (this.isEnabled()) {
            this.applyNormalColors();
        }
        else {
            this.applyDisabledColors();
        }
    }

    private void applyNormalColors() {
        this.setBackground(normalBackground);
        this.setForeground(normalForeground);
    }

    private void applyHoverColors() {
        this.setBackground(hoverBackground);
        this.setForeground(hoverForeground);
    }

    private void applyDisabledColors() {
        this.setBackground(Theme.BACKGROUND3);
        this.setForeground(Theme.TEXT_MUTED);
    }

    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        if (normalBackground != null) {
            if (enabled) {
                this.applyNormalColors();
            }
            else {
                this.applyDisabledColors();
            }
        }
    }

    public void mouseEntered(MouseEvent e) {
        if (this.isEnabled()) {
            this.applyHoverColors();
        }
    }

    public void mouseExited(MouseEvent e) {
        if (this.isEnabled()) {
            this.applyNormalColors();
        }
        else {
            this.applyDisabledColors();
        }
    }

    public void mouseClicked(MouseEvent e) {
    }

    public void mousePressed(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }
}
