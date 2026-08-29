import java.lang.*;
import frames.*;

public class Start {
    public static void main(String[] args) {
        Theme.apply();
        Login login = new Login();
        login.setVisible(true);
    }
}
