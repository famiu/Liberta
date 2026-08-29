package frames;

import java.lang.*;
import javax.swing.*;

public class LibertaTextArea extends JTextArea {
    public LibertaTextArea() {
        this("");
    }

    public LibertaTextArea(String text) {
        super(text);

        this.setFont(Theme.MAIN_FONT);
        this.setForeground(Theme.TEXT);
        this.setLineWrap(true);
        this.setWrapStyleWord(true);
    }

    // We need to disable editing and focus and remove the background and border when a text area
    // is only used to display text
    public void setDisplayOnly() {
        this.setEditable(false);
        this.setFocusable(false);
        this.setOpaque(false);
        this.setBorder(null);
    }
}
