package ui.frames;

import java.lang.*;
import java.time.*;
import java.time.format.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import entity.*;
import storage.*;

import ui.*;
import ui.components.*;
import ui.dialogs.*;
import ui.panels.*;

public class RegisterFrame extends LibertaFrame implements ActionListener {
    private JTextField displayNameField;
    private JTextField usernameField;
    private JTextField emailField;
    private JTextField dateOfBirthField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;

    private LibertaButton createAccountButton;
    private LibertaButton loginPromptButton;

    public RegisterFrame() {
        super("Create Account", new BorderLayout());

        LibertaScrollablePanel formPanel = new LibertaScrollablePanel(true);
        // Need to use GridBagLayout to center the registration form when there is extra vertical space
        // Reference: https://stackoverflow.com/questions/7223530/how-can-i-properly-center-a-jpanel-fixed-size-inside-a-jframe
        formPanel.setLayout(new GridBagLayout());
        formPanel.setBackground(Theme.BACKGROUND);
        formPanel.setBorder(BorderFactory.createEmptyBorder(28, 20, 28, 20));

        JPanel formContentPanel = new JPanel();
        formContentPanel.setLayout(new BoxLayout(formContentPanel, BoxLayout.Y_AXIS));
        formContentPanel.setOpaque(false);

        JLabel headingLabel = new JLabel("Create Account");
        headingLabel.setFont(Theme.BOLD_FONT.deriveFont(30f));
        headingLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        displayNameField = new JTextField();
        AuthFieldPanel displayNameFieldPanel = new AuthFieldPanel("Display Name", displayNameField);

        usernameField = new JTextField();
        AuthFieldPanel usernameFieldPanel = new AuthFieldPanel("Username", usernameField);

        emailField = new JTextField();
        AuthFieldPanel emailFieldPanel = new AuthFieldPanel("Email", emailField);

        dateOfBirthField = new JTextField();
        AuthFieldPanel dateOfBirthFieldPanel = new AuthFieldPanel("Date of Birth (YYYY-MM-DD)", dateOfBirthField);

        passwordField = new JPasswordField();
        AuthFieldPanel passwordFieldPanel = new AuthFieldPanel("Password", passwordField);

        confirmPasswordField = new JPasswordField();
        AuthFieldPanel confirmPasswordFieldPanel = new AuthFieldPanel("Confirm Password", confirmPasswordField);

        createAccountButton = new LibertaButton("Create Account");
        createAccountButton.setColors(Theme.ACCENT1, Theme.ACCENT2, Theme.TEXT, Theme.TEXT, true);
        createAccountButton.setMargin(new Insets(11, 28, 11, 28));
        createAccountButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        createAccountButton.addActionListener(this);
        this.setDefaultButton(createAccountButton);

        JLabel loginPromptLabel = new JLabel("Already have an account?");
        loginPromptLabel.setForeground(Theme.TEXT_MUTED);

        loginPromptButton = new LibertaButton("Sign in here");
        loginPromptButton.setMargin(new Insets(4, 4, 4, 4));
        loginPromptButton.addActionListener(this);

        FlowLayout loginPromptLayout = new FlowLayout(FlowLayout.CENTER, 4, 0);
        loginPromptLayout.setAlignOnBaseline(true);
        JPanel loginPromptPanel = new JPanel(loginPromptLayout);
        loginPromptPanel.setOpaque(false);
        loginPromptPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginPromptPanel.add(loginPromptLabel);
        loginPromptPanel.add(loginPromptButton);

        formContentPanel.add(headingLabel);
        formContentPanel.add(Box.createVerticalStrut(36));
        formContentPanel.add(displayNameFieldPanel);
        formContentPanel.add(Box.createVerticalStrut(12));
        formContentPanel.add(usernameFieldPanel);
        formContentPanel.add(Box.createVerticalStrut(12));
        formContentPanel.add(emailFieldPanel);
        formContentPanel.add(Box.createVerticalStrut(12));
        formContentPanel.add(dateOfBirthFieldPanel);
        formContentPanel.add(Box.createVerticalStrut(12));
        formContentPanel.add(passwordFieldPanel);
        formContentPanel.add(Box.createVerticalStrut(12));
        formContentPanel.add(confirmPasswordFieldPanel);
        formContentPanel.add(Box.createVerticalStrut(36));
        formContentPanel.add(createAccountButton);
        formContentPanel.add(Box.createVerticalStrut(14));
        formContentPanel.add(loginPromptPanel);

        formPanel.add(formContentPanel);

        JScrollPane formScrollPane = new JScrollPane(formPanel);
        formScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        formScrollPane.setBorder(null);
        formScrollPane.getViewport().setBackground(Theme.BACKGROUND);

        JScrollBar formScrollBar = formScrollPane.getVerticalScrollBar();
        formScrollBar.setUI(new LibertaScrollBarUI(Theme.TEXT_MUTED, Theme.BACKGROUND2));
        formScrollBar.setPreferredSize(new Dimension(18, 0));
        formScrollBar.setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6));
        formScrollBar.setBackground(Theme.BACKGROUND);

        panel.setBackground(Theme.BACKGROUND);
        panel.add(new BrandPanel(), BorderLayout.WEST);
        panel.add(formScrollPane, BorderLayout.CENTER);
    }

    private void showMessage(String message) {
        LibertaMessageDialog messageDialog = new LibertaMessageDialog(this, "Create Account", message);
        messageDialog.showDialog();
    }

    private void registerUser() {
        String displayName = displayNameField.getText().trim();
        String username = usernameField.getText().trim();
        String email = emailField.getText().trim();
        String dateOfBirthText = dateOfBirthField.getText().trim();
        String password = new String(passwordField.getPassword());
        String confirmPassword = new String(confirmPasswordField.getPassword());

        if (displayName.isEmpty()) {
            showMessage("Display name is required.");
            return;
        }
        else if (displayName.length() > UserValidation.MAX_DISPLAY_NAME_LENGTH) {
            showMessage("Display name cannot exceed " + UserValidation.MAX_DISPLAY_NAME_LENGTH + " characters.");
            return;
        }
        else if (username.isEmpty()) {
            showMessage("Username is required.");
            return;
        }
        else if (username.length() < UserValidation.MIN_USERNAME_LENGTH
                || username.length() > UserValidation.MAX_USERNAME_LENGTH) {
            showMessage("Username must be between " + UserValidation.MIN_USERNAME_LENGTH
                        + " and " + UserValidation.MAX_USERNAME_LENGTH + " characters.");
            return;
        }
        else if (!UserValidation.usernameHasValidCharacters(username)) {
            showMessage("Username can only contain letters, numbers, and underscores.");
            return;
        }
        else if (UserStorage.checkUser(username)) {
            showMessage("That username is already in use.");
            return;
        }
        else if (email.isEmpty()) {
            showMessage("Email is required.");
            return;
        }
        else if (!UserValidation.isValidEmail(email)) {
            showMessage("Please enter a valid email address.");
            return;
        }
        else if (dateOfBirthText.isEmpty()) {
            showMessage("Date of birth is required.");
            return;
        }

        // Only way to validate date format in Java is to try to parse it and catch the exception if it fails
        // Reference: https://stackoverflow.com/questions/55523906/how-to-give-error-message-when-user-input-wrong-format-of-date-in-java8
        LocalDate dateOfBirth;
        try {
            dateOfBirth = LocalDate.parse(dateOfBirthText);
        }
        catch (DateTimeParseException exception) {
            showMessage("Date of birth must use YYYY-MM-DD format.");
            return;
        }

        if (!UserValidation.meetsMinimumAge(dateOfBirth)) {
            showMessage("You must be at least " + UserValidation.MINIMUM_AGE + " years old to register.");
            return;
        }
        else if (password.isEmpty()) {
            showMessage("Password is required.");
            return;
        }
        else if (!UserValidation.passwordHasValidCharacters(password)) {
            showMessage("Password can only contain letters, numbers, and symbols.");
            return;
        }
        else if (password.length() < UserValidation.MIN_PASSWORD_LENGTH) {
            showMessage("Password must contain at least " + UserValidation.MIN_PASSWORD_LENGTH + " characters.");
            return;
        }
        else if (confirmPassword.isEmpty()) {
            showMessage("Please confirm your password.");
            return;
        }
        else if (!password.equals(confirmPassword)) {
            passwordField.setText("");
            confirmPasswordField.setText("");
            showMessage("The passwords do not match.");
            return;
        }

        UserAccount user = new UserAccount(username, password, displayName, email, "", dateOfBirth);
        UserStorage.addUser(user);

        showMessage("Account created successfully.");
        switchFrame(new LoginFrame());
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginPromptButton) {
            switchFrame(new LoginFrame());
        }
        else if (e.getSource() == createAccountButton) {
            registerUser();
        }
    }
}
