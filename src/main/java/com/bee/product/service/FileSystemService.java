package com.bee.product.service;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class FileSystemService {

    public List<String> listAllFiles(String directoryPath) {
        List<String> result = new ArrayList<>();
        File directory = new File(directoryPath);

        // ensure that the directory exists and is indeed a directory
        if(directory.exists() && directory.isDirectory()) {
            traverseDirectory(directory, result);
        } else {
            System.out.println("The provided directory path does not exist or is not a directory.");
        }

        return result;
    }

    private void traverseDirectory(File dir, List<String> holder) {
        // list all files in the current directory
        for(File file : dir.listFiles()) {
            if(file.isDirectory()) {
                // if it's a directory, recursive call
                traverseDirectory(file, holder);
            } else {
                // if it's a file, add path to holder
                holder.add(file.getAbsolutePath());
            }
        }
    }

    /**
     * Get the content of a file.
     *
     * @param filename The name of the file.
     * @return The content of the file.
     * @throws IOException If an I/O error occurs.
     */
    public String getFile(String filename) throws IOException {
        Path path = Paths.get(filename);

        if (!Files.exists(path)) {
            throw new IOException("File " + filename + " not found.");
        }

        byte[] bytes = Files.readAllBytes(path);
        return new String(bytes, StandardCharsets.UTF_8);
    }

    /**
     * Save the content to a file.
     *
     * @param content  The content to be saved.
     * @param filename The name of the file.
     * @return true if the file was saved, false otherwise.
     */
    public boolean saveFile(String content, String filename) {
        try {
            Path path = Paths.get(filename);
            Files.write(path, content.getBytes(StandardCharsets.UTF_8));
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

}