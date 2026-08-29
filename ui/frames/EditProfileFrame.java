package ui.frames;

import java.lang.*;
import java.io.*;
import java.nio.file.*;
import java.time.*;
import java.time.format.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;

import entity.*;
import storage.*;

import ui.*;
import ui.components.*;
import ui.dialogs.*;
import ui.panels.*;

public class EditProfileFrame extends LibertaFrame implements ActionListener, DocumentListener {
    private static final int PROFILE_PICTURE_SIZE = 128;

    private String username;

    private JTextField displayNameField;
    private JTextField bioField;
    private JTextField emailField;
    private JTextField dateOfBirthField;
    private JPasswordField newPasswordField;
    private JPasswordField confirmPasswordField;

    private LibertaButton backButton;
    private LibertaButton profilePictureButton;
    private LibertaButton removePictureButton;
    private LibertaButton cancelButton;
    private LibertaButton updateProfileButton;

    private File selectedProfilePicture;
    private boolean profilePictureRemoved;

    public EditProfileFrame(String username) {
        super("Edit Profile", new BorderLayout());

        this.username = username;
        UserAccount user = UserStorage.getUser(username);

        ScrollablePanel formPanel = new ScrollablePanel(true);
        // Need to use GridBagLayout to center the form when there is extra vertical space
        formPanel.setLayout(new GridBagLayout());
        formPanel.setBackground(Theme.BACKGROUND);
        formPanel.setBorder(BorderFactory.createEmptyBorder(24, 20, 28, 20));

        JPanel formContentPanel = new JPanel();
        formContentPanel.setLayout(new BoxLayout(formContentPanel, BoxLayout.Y_AXIS));
        formContentPanel.setOpaque(false);

        File profilePicture = UserStorage.getUserProfilePicture(username);
        profilePictureButton = new LibertaButton(createProfilePictureIcon(profilePicture));
        profilePictureButton.setColors(Theme.BACKGROUND, Theme.BACKGROUND2, Theme.TEXT, Theme.TEXT, true);
        profilePictureButton.setBorder(null);
        profilePictureButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        profilePictureButton.addActionListener(this);

        JLabel usernameLabel = new JLabel("@" + username);
        usernameLabel.setForeground(Theme.TEXT_MUTED);
        usernameLabel.setFont(Theme.MAIN_FONT.deriveFont(16f));
        usernameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        removePictureButton = new LibertaButton("Remove Picture");
        removePictureButton.setColors(Theme.DANGER, Theme.DANGER.brighter(), Theme.TEXT, Theme.TEXT, true);
        removePictureButton.setMargin(new Insets(8, 14, 8, 14));
        removePictureButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        removePictureButton.addActionListener(this);

        displayNameField = new JTextField(user.getDisplayName());
        displayNameField.getDocument().addDocumentListener(this);
        AuthFieldPanel displayNameFieldPanel = new AuthFieldPanel("Display Name", displayNameField);

        bioField = new JTextField(user.getBio());
        bioField.getDocument().addDocumentListener(this);
        AuthFieldPanel bioFieldPanel = new AuthFieldPanel("Bio", bioField);

        emailField = new JTextField(user.getEmail());
        emailField.getDocument().addDocumentListener(this);
        AuthFieldPanel emailFieldPanel = new AuthFieldPanel("Email", emailField);

        dateOfBirthField = new JTextField(user.getDateOfBirth().toString());
        dateOfBirthField.getDocument().addDocumentListener(this);
        AuthFieldPanel dateOfBirthFieldPanel = new AuthFieldPanel("Date of Birth (YYYY-MM-DD)", dateOfBirthField);

        newPasswordField = new JPasswordField();
        newPasswordField.getDocument().addDocumentListener(this);
        AuthFieldPanel newPasswordFieldPanel = new AuthFieldPanel("New Password (optional)", newPasswordField);

        confirmPasswordField = new JPasswordField();
        confirmPasswordField.getDocument().addDocumentListener(this);
        AuthFieldPanel confirmPasswordFieldPanel = new AuthFieldPanel("Confirm New Password", confirmPasswordField);

        cancelButton = new LibertaButton("Cancel");
        cancelButton.setColors(Theme.BACKGROUND2, Theme.BACKGROUND3, Theme.TEXT_MUTED, Theme.TEXT, true);
        cancelButton.setMargin(new Insets(11, 20, 11, 20));
        cancelButton.addActionListener(this);

        updateProfileButton = new LibertaButton("Update Profile");
        updateProfileButton.setColors(Theme.ACCENT1, Theme.ACCENT2, Theme.TEXT, Theme.TEXT, true);
        updateProfileButton.setMargin(new Insets(11, 20, 11, 20));
        updateProfileButton.addActionListener(this);
        updateProfileButton.setEnabled(false);
        this.setDefaultButton(updateProfileButton);

        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        actionPanel.setOpaque(false);
        actionPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        actionPanel.add(cancelButton);
        actionPanel.add(updateProfileButton);

        formContentPanel.add(profilePictureButton);
        formContentPanel.add(Box.createVerticalStrut(12));
        formContentPanel.add(usernameLabel);
        formContentPanel.add(Box.createVerticalStrut(12));
        formContentPanel.add(removePictureButton);
        formContentPanel.add(Box.createVerticalStrut(28));
        formContentPanel.add(displayNameFieldPanel);
        formContentPanel.add(Box.createVerticalStrut(12));
        formContentPanel.add(bioFieldPanel);
        formContentPanel.add(Box.createVerticalStrut(12));
        formContentPanel.add(emailFieldPanel);
        formContentPanel.add(Box.createVerticalStrut(12));
        formContentPanel.add(dateOfBirthFieldPanel);
        formContentPanel.add(Box.createVerticalStrut(12));
        formContentPanel.add(newPasswordFieldPanel);
        formContentPanel.add(Box.createVerticalStrut(12));
        formContentPanel.add(confirmPasswordFieldPanel);
        formContentPanel.add(Box.createVerticalStrut(28));
        formContentPanel.add(actionPanel);

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

        ImageIcon backIcon = new ImageIcon("./assets/icons/png/32/back-button.png");
        backButton = new LibertaButton(backIcon);
        backButton.setBorder(null);
        backButton.addActionListener(this);

        // We want the back button in the top-left without giving it its own row, this is
        // apparently done by using JLayeredPane, which allows putting panels on top of each other.
        // JLaayeredPane has no layout manager, so we need to use OverlayLayout with it.
        // We can align back button to the top-left using setAlignmentX() and setAlignmentY() on the back panel.
        // Also need to limit the size of the back panel, otherwise OverlayLayout will stretch it.
        // References:
        // https://stackoverflow.com/questions/8792075/overlay-panel-above-another
        // https://stackoverflow.com/questions/38703262/java-layout-with-component-always-in-top-right/38703764#38703764
        // https://docs.oracle.com/javase/tutorial/uiswing/components/layeredpane.html
        // https://docs.oracle.com/en/java/javase/26/docs/api/java.desktop/javax/swing/OverlayLayout.html
        JPanel backPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        backPanel.setOpaque(false);
        backPanel.setBorder(BorderFactory.createEmptyBorder(16, 20, 0, 0));
        backPanel.add(backButton);
        backPanel.setMaximumSize(backPanel.getPreferredSize());
        backPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        backPanel.setAlignmentY(Component.TOP_ALIGNMENT);
        JLayeredPane content = new JLayeredPane();
        content.setLayout(new OverlayLayout(content));
        formScrollPane.setAlignmentX(Component.LEFT_ALIGNMENT);
        formScrollPane.setAlignmentY(Component.TOP_ALIGNMENT);
        content.add(formScrollPane, JLayeredPane.DEFAULT_LAYER);
        content.add(backPanel, JLayeredPane.PALETTE_LAYER);

        panel.setBackground(Theme.BACKGROUND);
        panel.add(content, BorderLayout.CENTER);
    }

    private static ImageIcon createProfilePictureIcon(File pictureFile) {
        ImageIcon profilePicture = new ImageIcon(pictureFile.getPath());
        Image scaledImage = profilePicture.getImage().getScaledInstance(PROFILE_PICTURE_SIZE,
            PROFILE_PICTURE_SIZE, Image.SCALE_SMOOTH);
        profilePicture.setImage(scaledImage);
        return profilePicture;
    }

    private void chooseProfilePicture() {
        // JFileChooser doesn't use the operating system's native file chooser, so we are using FileDialog instead.
        // References:
        // https://stackoverflow.com/questions/10745198/how-to-use-the-default-file-chooser-for-the-operating-system
        // https://docs.oracle.com/en/java/javase/26/docs/api/java.desktop/java/awt/FileDialog.html
        FileDialog pictureDialog = new FileDialog(this, "Choose Profile Picture", FileDialog.LOAD);
        // Need to use setFilenameFilter() to filter by file type
        // Reference: https://stackoverflow.com/questions/66685956/how-to-set-filter-for-file-type-in-filedialog
        pictureDialog.setFilenameFilter((dir, name) -> {
            return name.toLowerCase().endsWith(".png") || name.toLowerCase().endsWith(".jpg");
        });
        pictureDialog.setVisible(true);

        String filename = pictureDialog.getFile();
        String directory = pictureDialog.getDirectory();
        pictureDialog.dispose();

        if (filename == null) {
            return;
        }

        // setFilenameFilter() might not always work correctly, so we need to validate extension anyway
        // Reference: https://docs.oracle.com/en/java/javase/26/docs/api/java.desktop/java/awt/FileDialog.html#setFilenameFilter(java.io.FilenameFilter)
        String lowercaseFilename = filename.toLowerCase();
        if (!lowercaseFilename.endsWith(".png") && !lowercaseFilename.endsWith(".jpg")) {
            showMessage("Profile picture must be a PNG or JPG image.");
            return;
        }

        File picture = new File(directory, filename);
        File currentPicture = UserStorage.getUserProfilePicture(username);
        if (isSameFile(picture, currentPicture)) {
            // If the user selects the same picture as current profile picture, treat it like
            // undoing any changes to profile picture.
            selectedProfilePicture = null;
            profilePictureRemoved = false;
            profilePictureButton.setIcon(createProfilePictureIcon(currentPicture));
            updateUpdateProfileButton();
            return;
        }

        selectedProfilePicture = picture;
        profilePictureRemoved = false;
        profilePictureButton.setIcon(createProfilePictureIcon(picture));
        updateUpdateProfileButton();
    }

    private void removeProfilePicture() {
        selectedProfilePicture = null;
        // If user already has the default profile picture, then removing profile picture is not a change.
        File currentPicture = UserStorage.getUserProfilePicture(username);
        File defaultPicture = UserStorage.getDefaultProfilePicture();
        profilePictureRemoved = !isSameFile(currentPicture, defaultPicture);
        profilePictureButton.setIcon(createProfilePictureIcon(defaultPicture));
        updateUpdateProfileButton();
    }

    private static boolean isSameFile(File firstFile, File secondFile) {
        // Since File.equals() only checks the path, absolute and relative path to the same file
        // will not show as equal. Need to normalize and convert to absolute path before comparing.
        // References:
        // https://stackoverflow.com/questions/59803475/how-does-one-compare-path-equivalence-in-java
        // https://docs.oracle.com/en/java/javase/26/docs/api/java.base/java/nio/file/Path.html
        Path firstPath = firstFile.toPath().toAbsolutePath().normalize();
        Path secondPath = secondFile.toPath().toAbsolutePath().normalize();
        return firstPath.equals(secondPath);
    }

    // Update the enabled state of the Update Profile button based on whether any form fields have changed.
    private void updateUpdateProfileButton() {
        UserAccount user = UserStorage.getUser(username);
        boolean displayNameChanged = !displayNameField.getText().trim().equals(user.getDisplayName());
        boolean bioChanged = !bioField.getText().trim().equals(user.getBio());
        boolean emailChanged = !emailField.getText().trim().equals(user.getEmail());
        boolean dateOfBirthChanged = !dateOfBirthField.getText().trim().equals(user.getDateOfBirth().toString());
        boolean passwordChanged = newPasswordField.getPassword().length > 0
            || confirmPasswordField.getPassword().length > 0;
        boolean profilePictureChanged = selectedProfilePicture != null || profilePictureRemoved;

        boolean profileChanged = displayNameChanged || bioChanged || emailChanged || dateOfBirthChanged
            || passwordChanged || profilePictureChanged;
        updateProfileButton.setEnabled(profileChanged);
    }

    private void showMessage(String message) {
        LibertaMessageDialog messageDialog = new LibertaMessageDialog(this, "Edit Profile", message);
        messageDialog.showDialog();
    }

    private void updateProfile() {
        String displayName = displayNameField.getText().trim();
        String bio = bioField.getText().trim();
        String email = emailField.getText().trim();
        String dateOfBirthText = dateOfBirthField.getText().trim();
        String newPassword = new String(newPasswordField.getPassword());
        String confirmPassword = new String(confirmPasswordField.getPassword());

        if (displayName.isEmpty()) {
            showMessage("Display name is required.");
            return;
        }
        else if (displayName.length() > UserValidation.MAX_DISPLAY_NAME_LENGTH) {
            showMessage("Display name cannot exceed " + UserValidation.MAX_DISPLAY_NAME_LENGTH + " characters.");
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
            showMessage("You must be at least " + UserValidation.MINIMUM_AGE + " years old.");
            return;
        }

        boolean changingPassword = !newPassword.isEmpty() || !confirmPassword.isEmpty();
        if (changingPassword) {
            if (newPassword.isEmpty()) {
                showMessage("Please enter a new password.");
                return;
            }
            else if (!UserValidation.passwordHasValidCharacters(newPassword)) {
                showMessage("Password can only contain letters, numbers, and symbols.");
                return;
            }
            else if (newPassword.length() < UserValidation.MIN_PASSWORD_LENGTH) {
                showMessage("Password must contain at least " + UserValidation.MIN_PASSWORD_LENGTH + " characters.");
                return;
            }
            else if (confirmPassword.isEmpty()) {
                showMessage("Please confirm your new password.");
                return;
            }
            else if (!newPassword.equals(confirmPassword)) {
                newPasswordField.setText("");
                confirmPasswordField.setText("");
                showMessage("The passwords do not match.");
                return;
            }
        }

        UserAccount user = UserStorage.getUser(username);
        user.setDisplayName(displayName);
        user.setBio(bio);
        user.setEmail(email);
        user.setDateOfBirth(dateOfBirth);
        if (changingPassword) {
            user.setPassword(newPassword);
        }
        UserStorage.updateUser(user);

        if (profilePictureRemoved) {
            UserStorage.deleteUserProfilePicture(username);
        }
        else if (selectedProfilePicture != null) {
            UserStorage.setUserProfilePicture(username, selectedProfilePicture);
        }

        showMessage("Profile updated successfully.");
        switchFrame(new ProfileFrame(username));
    }

    private void returnToProfile() {
        switchFrame(new ProfileFrame(username));
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == backButton || e.getSource() == cancelButton) {
            returnToProfile();
        }
        else if (e.getSource() == profilePictureButton) {
            chooseProfilePicture();
        }
        else if (e.getSource() == removePictureButton) {
            removeProfilePicture();
        }
        else if (e.getSource() == updateProfileButton) {
            updateProfile();
        }
    }

    public void insertUpdate(DocumentEvent e) {
        updateUpdateProfileButton();
    }

    public void removeUpdate(DocumentEvent e) {
        updateUpdateProfileButton();
    }

    public void changedUpdate(DocumentEvent e) {
        updateUpdateProfileButton();
    }
}
