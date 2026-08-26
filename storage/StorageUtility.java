package storage;

import java.io.*;

public class StorageUtility {
    public static void ensurePath(String directoryPath) {
        File directory = new File(directoryPath);

        if (!directory.exists()) {
            if (!directory.mkdirs()) {
                System.out.println("Failed to create " + directoryPath);
            }
        } else if (!directory.isDirectory()) {
            System.out.println(directoryPath + " is not a directory");
        }
    }

    public static void ensurePath(String directoryPath, String fileName) {
        ensurePath(directoryPath);

        File file = new File(directoryPath, fileName);

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException ioe) {
                System.out.println("Failed to create " + fileName);
            }
        }
    }
}
