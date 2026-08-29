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
}
