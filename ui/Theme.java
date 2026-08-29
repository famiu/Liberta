package ui;

import java.lang.*;
import javax.swing.*;
import java.awt.*;
import java.io.*;

public final class Theme {
    public static final Color BACKGROUND = new Color(23, 21, 26);
    public static final Color BACKGROUND2 = new Color(30, 28, 36);
    public static final Color BACKGROUND3 = new Color(43, 40, 49);
    public static final Color ACCENT1 = new Color(139, 92, 246);
    public static final Color ACCENT2 = new Color(167, 139, 250);
    public static final Color ACCENT3 = new Color(76, 29, 149);
    public static final Color DANGER = new Color(239, 68, 68);
    public static final Color TEXT = Color.WHITE;
    public static final Color TEXT_MUTED = new Color(205, 201, 211);

    public static final Font MAIN_FONT = loadFont("./assets/fonts/Inter/Inter-Regular.ttf", Font.PLAIN, 16f);
    public static final Font BOLD_FONT = loadFont("./assets/fonts/Inter/Inter-SemiBold.ttf", Font.BOLD, 16f);
    public static final Font ITALIC_FONT = loadFont("./assets/fonts/Inter/Inter-Italic.ttf", Font.ITALIC, 16f);

    // Make the constructor private to prevent instantiation
    private Theme() {
    }

    // Set default colors and fonts for Swing components using UIManager to reduce boilerplate code
    // References:
    // https://stackoverflow.com/questions/9282349/how-to-set-all-java-swing-gui-component-backgrounds-and-foregroundfonts-colors
    // https://docs.oracle.com/javase/8/docs/api/javax/swing/UIManager.html
    // Call this before creating frames so Swing uses these defaults everywhere.
    public static void apply() {
        UIManager.put("Panel.background", BACKGROUND);

        UIManager.put("Label.foreground", TEXT);
        UIManager.put("Label.font", MAIN_FONT);

        UIManager.put("Button.background", ACCENT1);
        UIManager.put("Button.foreground", TEXT);
        UIManager.put("Button.font", BOLD_FONT);

        UIManager.put("TextField.background", BACKGROUND2);
        UIManager.put("TextField.foreground", TEXT);
        UIManager.put("TextField.caretForeground", TEXT);
        UIManager.put("TextField.font", MAIN_FONT);

        UIManager.put("PasswordField.background", BACKGROUND2);
        UIManager.put("PasswordField.foreground", TEXT);
        UIManager.put("PasswordField.caretForeground", TEXT);
        UIManager.put("PasswordField.font", MAIN_FONT);

        UIManager.put("TextArea.background", BACKGROUND2);
        UIManager.put("TextArea.foreground", TEXT);
        UIManager.put("TextArea.caretForeground", TEXT);
        UIManager.put("TextArea.font", MAIN_FONT);

        UIManager.put("CheckBox.background", BACKGROUND);
        UIManager.put("CheckBox.foreground", TEXT);
        UIManager.put("CheckBox.font", MAIN_FONT);

        UIManager.put("RadioButton.background", BACKGROUND);
        UIManager.put("RadioButton.foreground", TEXT);
        UIManager.put("RadioButton.font", MAIN_FONT);

        UIManager.put("ComboBox.background", BACKGROUND2);
        UIManager.put("ComboBox.foreground", TEXT);
        UIManager.put("ComboBox.font", MAIN_FONT);

        UIManager.put("Table.background", BACKGROUND2);
        UIManager.put("Table.foreground", TEXT);
        UIManager.put("Table.font", MAIN_FONT);

        UIManager.put("TableHeader.background", BACKGROUND3);
        UIManager.put("TableHeader.foreground", TEXT);
        UIManager.put("TableHeader.font", BOLD_FONT);
    }

    // Since we use a custom font, need to load the TTF from file instead of using font name
    // Reference: https://stackoverflow.com/questions/16761630/font-createfont-set-color-and-size-java-awt-font
    private static Font loadFont(String path, int style, float size) {
        try {
            Font font = Font.createFont(Font.TRUETYPE_FONT, new File(path));
            return font.deriveFont(style, size);
        } catch (IOException | FontFormatException e) {
            System.out.println("Error loading font");
            return new Font("SansSerif", style, (int) size);
        }
    }
}
