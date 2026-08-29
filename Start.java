import java.lang.*;
import ui.*;
import ui.frames.*;

public class Start {
    public static void main(String[] args) {
        Theme.apply();
        LoginFrame login = new LoginFrame();
        login.setVisible(true);
    }
}
