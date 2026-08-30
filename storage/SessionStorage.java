package storage;

import java.io.*;
import java.util.*;

public class SessionStorage {
    static {
        StorageUtility.ensurePath("data", "session.txt");
    }

    // Don't need constructor since class is fully static, so make it private to prevent instantiation
    private SessionStorage() {
    }

    public static String getLoggedInUser() {
        try (Scanner reader = new Scanner(new File("data/session.txt"))) {
            if (reader.hasNextLine()) {
                String username = reader.nextLine().trim();
                if (!username.isEmpty()) {
                    return username;
                }
            }
        }
        catch (IOException e) {
            System.out.println("Error reading session file.");
        }

        return null;
    }

    public static void setLoggedInUser(String username) {
        try (FileWriter writer = new FileWriter("data/session.txt")) {
            writer.write(username);
        }
        catch (IOException e) {
            System.out.println("Error updating session file.");
        }
    }

    public static void clearLoggedInUser() {
        setLoggedInUser("");
    }
}
