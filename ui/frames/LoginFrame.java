package ui.frames;

import java.lang.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

import storage.*;

import ui.*;
import ui.components.*;
import ui.dialogs.*;
import ui.panels.*;

public class LoginFrame extends LibertaFrame implements ActionListener {
    private JTextField usernameField;
    private JPasswordField passwordField;

    private LibertaButton userTabButton;
    private LibertaButton adminTabButton;
    private LibertaButton selectedTab;
    private LibertaButton signInButton;
    private LibertaButton registerPromptButton;

    private JPanel promptPanel;
    private CardLayout promptLayout;

    public LoginFrame() {
        super("Login", new BorderLayout());

        JPanel formContentPanel = new JPanel();
        formContentPanel.setLayout(new BoxLayout(formContentPanel, BoxLayout.Y_AXIS));
        formContentPanel.setOpaque(false);

        JLabel headingLabel = new JLabel("Login");
        headingLabel.setFont(Theme.BOLD_FONT.deriveFont(30f));
        headingLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        userTabButton = new LibertaButton("User");
        userTabButton.setFont(Theme.BOLD_FONT.deriveFont(20f));
        userTabButton.setMargin(new Insets(8, 24, 8, 24));
        userTabButton.setBorderPainted(true);
        userTabButton.setColors(Theme.BACKGROUND, Theme.BACKGROUND, Theme.TEXT, Theme.ACCENT1, false);
        userTabButton.addActionListener(this);

        adminTabButton = new LibertaButton("Admin");
        adminTabButton.setFont(Theme.BOLD_FONT.deriveFont(20f));
        adminTabButton.setMargin(new Insets(8, 24, 8, 24));
        adminTabButton.setBorderPainted(true);
        adminTabButton.setColors(Theme.BACKGROUND, Theme.BACKGROUND, Theme.TEXT, Theme.ACCENT1, false);
        adminTabButton.addActionListener(this);

        selectedTab = userTabButton;

        JPanel accountTypePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        accountTypePanel.setOpaque(false);
        accountTypePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        accountTypePanel.add(userTabButton);
        accountTypePanel.add(adminTabButton);

        usernameField = new JTextField();
        AuthFieldPanel usernameFieldPanel = new AuthFieldPanel("Username", usernameField);

        passwordField = new JPasswordField();
        AuthFieldPanel passwordFieldPanel = new AuthFieldPanel("Password", passwordField);

        signInButton = new LibertaButton("Sign In");
        signInButton.setColors(Theme.ACCENT1, Theme.ACCENT2, Theme.TEXT, Theme.TEXT, true);
        signInButton.setMargin(new Insets(11, 28, 11, 28));
        signInButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        signInButton.addActionListener(this);
        this.setDefaultButton(signInButton);

        JLabel registerPromptLabel = new JLabel("Don't have an account?");
        registerPromptLabel.setForeground(Theme.TEXT_MUTED);

        registerPromptButton = new LibertaButton("Register here");
        registerPromptButton.setMargin(new Insets(4, 4, 4, 4));
        registerPromptButton.addActionListener(this);

        FlowLayout registerPromptLayout = new FlowLayout(FlowLayout.CENTER, 4, 0);
        registerPromptLayout.setAlignOnBaseline(true);
        JPanel registerPromptPanel = new JPanel(registerPromptLayout);
        registerPromptPanel.setOpaque(false);
        registerPromptPanel.add(registerPromptLabel);
        registerPromptPanel.add(registerPromptButton);

        JPanel emptyPromptPanel = new JPanel();
        emptyPromptPanel.setOpaque(false);

        // Hiding the registration prompt changes the form's height and moves the other components, so we have to use
        // CardLayout to prevent the layout from moving when toggling between user and admin login.
        // Reference: https://stackoverflow.com/questions/6141321/setvisiblefalse-changes-the-layout-of-my-components-within-my-panel
        promptLayout = new CardLayout();
        promptPanel = new JPanel(promptLayout);
        promptPanel.setOpaque(false);
        promptPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        promptPanel.add(registerPromptPanel, "Visible");
        promptPanel.add(emptyPromptPanel, "Hidden");

        formContentPanel.add(headingLabel);
        formContentPanel.add(Box.createVerticalStrut(22));
        formContentPanel.add(accountTypePanel);
        formContentPanel.add(Box.createVerticalStrut(24));
        formContentPanel.add(usernameFieldPanel);
        formContentPanel.add(Box.createVerticalStrut(16));
        formContentPanel.add(passwordFieldPanel);
        formContentPanel.add(Box.createVerticalStrut(28));
        formContentPanel.add(signInButton);
        formContentPanel.add(Box.createVerticalStrut(18));
        formContentPanel.add(promptPanel);

        // Need to use GridBagLayout to keep the login form in the middle of the available space
        // Reference: https://stackoverflow.com/questions/7223530/how-can-i-properly-center-a-jpanel-fixed-size-inside-a-jframe
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Theme.BACKGROUND);
        formPanel.setBorder(BorderFactory.createEmptyBorder(24, 24, 24, 24));
        formPanel.add(formContentPanel);

        panel.setBackground(Theme.BACKGROUND);
        panel.add(new BrandPanel(), BorderLayout.WEST);
        panel.add(formPanel, BorderLayout.CENTER);

        updateSelectedTab();
    }

    private void updateSelectedTab() {
        Border activeBorder = BorderFactory.createMatteBorder(0, 0, 3, 0, Theme.ACCENT1);
        Border inactiveBorder = BorderFactory.createEmptyBorder(0, 0, 3, 0);

        if (selectedTab == userTabButton) {
            userTabButton.setBorder(activeBorder);
            adminTabButton.setBorder(inactiveBorder);
            promptLayout.show(promptPanel, "Visible");
        }
        else {
            userTabButton.setBorder(inactiveBorder);
            adminTabButton.setBorder(activeBorder);
            promptLayout.show(promptPanel, "Hidden");
        }
    }

    private void showMessage(String title, String message) {
        LibertaMessageDialog messageDialog = new LibertaMessageDialog(this, title, message);
        messageDialog.showDialog();
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == userTabButton) {
            selectedTab = userTabButton;
            updateSelectedTab();
        }
        else if (e.getSource() == adminTabButton) {
            selectedTab = adminTabButton;
            updateSelectedTab();
        }
        else if (e.getSource() == registerPromptButton) {
            switchFrame(new RegisterFrame());
        }
        else if (e.getSource() == signInButton) {
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword());

            if (username.isEmpty() || password.isEmpty()) {
                showMessage("Login", "Please fill in all fields.");
            }
            else if (selectedTab == userTabButton) {
                if (!UserStorage.checkUser(username)) {
                    showMessage("Login", "This user does not exist.");
                }
                else if (!UserStorage.checkPassword(username, password)) {
                    passwordField.setText("");
                    showMessage("Login", "The password is incorrect.");
                }
                else {
                    SessionStorage.setLoggedInUser(username);
                    switchFrame(new HomeFrame(username));
                }
            }
            else {
                if (!AdminStorage.checkAdmin(username)) {
                    showMessage("Login", "This admin does not exist.");
                }
                else if (!AdminStorage.checkPassword(username, password)) {
                    passwordField.setText("");
                    showMessage("Login", "The password is incorrect.");
                }
            }
        }
    }
}
