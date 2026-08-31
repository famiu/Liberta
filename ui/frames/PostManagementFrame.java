package ui.frames;

import java.lang.*;
import javax.swing.*;

import ui.panels.*;

public class PostManagementFrame extends ManagementFrame {
    public PostManagementFrame() {
        super("Post Management");

        ManagementHeaderPanel header = new ManagementHeaderPanel();
        header.addHeaderLabel("Id", 0, 100, 1.0, SwingConstants.CENTER);
        header.addHeaderLabel("Author", 1, 200, 2.0);
        header.addHeaderLabel("Content", 2, 300, 3.0);
        header.addHeaderLabel("Action", 3, 100, 1.0, SwingConstants.CENTER);

        this.setManagementContent(header, new PostManagementListPanel());
    }
}
