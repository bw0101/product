package com.bee.product.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

@SpringBootTest
@ActiveProfiles("test")
public class FqdnParserServiceTest {

    @Autowired
    private FqdnParserService fqdnParserService;

    /**
     * positive tests
     * testParseFqdn: This tests the parseFqdn method by verifying the returned FQDN matches the expected value.
     * testGetFilename: This tests the getFilename method by validating that the returned filename matches what is expected.
     * testGetFileExtension: This tests getFileExtension by checking if the resolved extension is as expected.
     * testGetFilePrefix: This tests getFilePrefix. The test iterates over files with different prefixes. It does not check for a specific prefix but confirms the presence of a prefix.
     * @throws Exception
     */
    @Test
    public void testParseFqdn() throws Exception {
        String fqdn = fqdnParserService.parseFqdn("https://www.example.com/any/path/file.txt");
        assertEquals("www.example.com", fqdn);
    }

    @Test
    public void testGetFilename() throws Exception {
        String filename = fqdnParserService.getFilename("https://www.example.com/any/path/file.txt");
        assertEquals("file.txt", filename);
    }

    @Test
    public void testGetFileExtension() throws Exception {
        String extension = fqdnParserService.getFileExtension("https://www.example.com/any/path/file.txt");
        assertEquals("txt", extension);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "https://www.example.com/PREFIX_ONE_file.txt",
            "https://www.example.com/PREFIX_TWO_file.txt",
            "https://www.example.com/PREFIX_THREE_file.txt"
    })
    public void testGetFilePrefix(String url) throws Exception {
        String file = fqdnParserService.getFilePrefix(url);
        assertTrue(file.startsWith("PREFIX_"));
    }

    /***
     * negative tests
     * Here's what these test cases do:
     * Test the methods with an invalid URL, expecting a URISyntaxException.
     * Test getFileExtension with a URL that has no file extension, expecting an empty string as the result.
     * Test getFilePrefix with a URL where the filename has no matching prefix, expecting an empty string as the result.
     */
    @Test
    public void testParseFqdnWithInvalidUrl() {
        assertThrows(URISyntaxException.class, () -> fqdnParserService.parseFqdn("Invalid URL"));
    }

    @Test
    public void testGetFilenameWithInvalidUrl() {
        assertThrows(URISyntaxException.class, () -> fqdnParserService.getFilename("Invalid URL"));
    }

    @Test
    public void testGetFileExtensionWithInvalidUrl() {
        assertThrows(URISyntaxException.class, () -> fqdnParserService.getFileExtension("Invalid URL"));
    }

    @Test
    public void testGetFilePrefixWithInvalidUrl() {
        assertThrows(URISyntaxException.class, () -> fqdnParserService.getFilePrefix("Invalid URL"));
    }

    @Test
    public void testGetFileExtensionWithNoExtension() throws URISyntaxException {
        String extension = fqdnParserService.getFileExtension("https://www.example.com/file");
        assertEquals("", extension);
    }

    @Test
    public void testGetFilePrefixWithNoMatch() throws URISyntaxException {
        String prefix = fqdnParserService.getFilePrefix("https://www.example.com/file.txt");
        assertEquals("", prefix);
    }

    /**
     * The following test cases are for the FileSystemService class.
     * Mock the listAllFiles method to return a list of files.
     */


   // FileSystemService dependencyService;
    @Mock
    DependencyService dependencyService;

    @Test
    public void testFetchUrlContent() throws Exception {
        // Arrange
        String url = "https://www.example.com";
        String expectedContent = "Hello, World!";
        doReturn(expectedContent).when(dependencyService).fetchUrlContent(url);

        // Act
        String actualContent = fqdnParserService.fetchUrlContent(url);  // Assuming this method exists in the FqdnParserService

        // Assert
        assertEquals(expectedContent, actualContent);
    }

    @Test
    public void testFetchUrlContent_Failure() throws Exception {
        // Arrange
        String url = "https://www.example.com";
        doThrow(new RuntimeException("Fetch Failed")).when(dependencyService).fetchUrlContent(url);

        // Act and Assert
        assertThrows(RuntimeException.class, () -> fqdnParserService.fetchUrlContent(url));
    }

}
