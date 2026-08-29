package frames;

import java.lang.*;

public class DeleteAccountDialog extends DeleteDialog {
    public DeleteAccountDialog(LibertaFrame parentFrame) {
        super(parentFrame, "Delete Account", 540, 240);

        this.setDeleteButtonText("Delete Account");
        String message = "Your profile, posts, and likes will be permanently deleted.\n"
            + "This action cannot be undone.";
        this.setMessage("Delete your account?", message);
    }
}
