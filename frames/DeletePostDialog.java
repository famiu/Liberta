package frames;

import java.lang.*;

public class DeletePostDialog extends DeleteDialog {
    public DeletePostDialog(LibertaFrame parentFrame) {
        super(parentFrame, "Delete Post", 460, 220);

        this.setDeleteButtonText("Delete Post");
        this.setMessage("Delete this post?", "This action cannot be undone.");
    }
}
