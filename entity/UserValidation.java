package entity;

import java.lang.*;
import java.time.*;

// This class contains static methods for validating user details from frontend before storing them in the database.
public final class UserValidation {
    public static final int MAX_DISPLAY_NAME_LENGTH = 50;
    public static final int MIN_USERNAME_LENGTH = 3;
    public static final int MAX_USERNAME_LENGTH = 20;
    public static final int MIN_PASSWORD_LENGTH = 8;
    public static final int MINIMUM_AGE = 16;

    private static final char[] ALLOWED_PASSWORD_SYMBOLS = {
        '!', '@', '#', '$', '%', '^', '&', '*',
        '(', ')', '_', '+', '-', '=', '.', ',', '?'
    };

    private UserValidation() {
    }

    public static boolean usernameHasValidCharacters(String username) {
        for (int i = 0; i < username.length(); i++) {
            char usernameCharacter = username.charAt(i);
            boolean isLowercaseLetter = usernameCharacter >= 'a' && usernameCharacter <= 'z';
            boolean isUppercaseLetter = usernameCharacter >= 'A' && usernameCharacter <= 'Z';
            boolean isDigit = usernameCharacter >= '0' && usernameCharacter <= '9';
            boolean isUnderscore = usernameCharacter == '_';

            if (!isLowercaseLetter && !isUppercaseLetter && !isDigit && !isUnderscore) {
                return false;
            }
        }

        return true;
    }

    public static boolean passwordHasValidCharacters(String password) {
        for (int i = 0; i < password.length(); i++) {
            char passwordCharacter = password.charAt(i);
            boolean isLowercaseLetter = passwordCharacter >= 'a' && passwordCharacter <= 'z';
            boolean isUppercaseLetter = passwordCharacter >= 'A' && passwordCharacter <= 'Z';
            boolean isDigit = passwordCharacter >= '0' && passwordCharacter <= '9';
            boolean isAllowedSymbol = false;

            for (int j = 0; j < ALLOWED_PASSWORD_SYMBOLS.length; j++) {
                if (passwordCharacter == ALLOWED_PASSWORD_SYMBOLS[j]) {
                    isAllowedSymbol = true;
                    break;
                }
            }

            if (!isLowercaseLetter && !isUppercaseLetter && !isDigit && !isAllowedSymbol) {
                return false;
            }
        }

        return true;
    }

    // Validate email format
    // I could have just used a regex directly, but avoided it due to concerns about course restrictions
    public static boolean isValidEmail(String email) {
        if (email == null || email.isEmpty() || !email.contains("@") || email.contains(" ")) {
            return false;
        }
        String parts[] = email.split("@");
        if (parts.length != 2) {
            return false;
        }
        String username = parts[0];
        String domain = parts[1];
        if (!domain.contains(".")) {
            return false;
        }
        // Need to escape the dot since split uses regex and dot is a special character in regex
        String domainParts[] = domain.split("\\.");
        if (domainParts.length != 2) {
            return false;
        }
        String domainName = domainParts[0];
        String domainExtension = domainParts[1];
        // Username and domain name can't be empty, and domain extension can't be less than 2 characters
        if (username.length() < 1 || domainName.length() < 1 || domainExtension.length() < 2) {
            return false;
        }
        return true;
    }

    public static boolean meetsMinimumAge(LocalDate dateOfBirth) {
        // References:
        // https://stackoverflow.com/questions/74859459/find-the-minimum-birthdate-of-people-based-on-a-given-age
        // https://docs.oracle.com/javase/8/docs/api/java/time/LocalDate.html
        LocalDate today = LocalDateTime.now().toLocalDate();
        LocalDate minimumDateOfBirth = today.minusYears(MINIMUM_AGE);
        return !dateOfBirth.isAfter(minimumDateOfBirth);
    }
}
