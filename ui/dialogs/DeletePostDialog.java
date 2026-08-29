package ui.dialogs;

import java.lang.*;

import ui.frames.*;

public class DeletePostDialog extends DeleteDialog {
    public DeletePostDialog(LibertaFrame parentFrame) {
        super(parentFrame, "Delete Post", 460, 220);

        this.setDeleteButtonText("Delete Post");
        this.setMessage("Delete this post?", "This action cannot be undone.");
    }
}
