package storage;

import java.io.*;

class StorageUtility {
    // Don't need constructor since class is fully static, so make it private to prevent instantiation
    private StorageUtility() {
    }

    static void ensurePath(String directoryPath) {
        File directory = new File(directoryPath);

        if (!directory.exists()) {
            if (!directory.mkdirs()) {
                System.out.println("Failed to create " + directoryPath);
            }
        } else if (!directory.isDirectory()) {
            System.out.println(directoryPath + " is not a directory");
        }
    }

    static void ensurePath(String directoryPath, String fileName) {
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
