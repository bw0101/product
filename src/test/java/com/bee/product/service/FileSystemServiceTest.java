package com.bee.product.service;

import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class FileSystemServiceTest {

    @InjectMocks
    private FileSystemService fileSystemService;

    private static final String TEST_FILENAME = "sample.txt";
    private static final String TEST_CONTENT = "Hello, World in a text file!";

    @Test
    public void saveFileTest() throws IOException {
        boolean isSaved = fileSystemService.saveFile(TEST_CONTENT, TEST_FILENAME);
        assertTrue(isSaved, "File should be saved successfully");
    }

    @Test
    public void getFileTest() throws IOException {
        when(fileSystemService.getFile(TEST_FILENAME)).thenReturn(TEST_CONTENT);
        String content = fileSystemService.getFile(TEST_FILENAME);
        assertEquals(TEST_CONTENT, content, "File content should match");
    }
}
