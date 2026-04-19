package com.training.client.file;

import java.io.*;
import java.nio.file.*;

public class UserFileService {
    
    public void writeToFile(String fileName, String content) throws IOException {
        Files.writeString(Path.of(fileName), content);
    }

    public String readFromFile(String fileName) throws IOException {
        return Files.readString(Path.of(fileName));
    }

    public void copyFile(String source, String destination) throws IOException {
        Files.copy(Path.of(source), Path.of(destination), StandardCopyOption.REPLACE_EXISTING);
    }

    public void moveFile(String source, String destination) throws IOException {
        Files.move(Path.of(source), Path.of(destination), StandardCopyOption.REPLACE_EXISTING);
    }

    public void deleteFile(String fileName) throws IOException {
        Files.deleteIfExists(Path.of(fileName));
    }

    public boolean fileExists(String fileName) {
        return Files.exists(Path.of(fileName));
    }

    public long getFileSize(String fileName) throws IOException {
        return Files.size(Path.of(fileName));
    }
}