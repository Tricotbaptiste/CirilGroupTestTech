package com.adventurer.file.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.io.TempDir;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("FileLoader Tests")
class ExternalFileLoaderTest {

    private FileLoader fileLoader;
    
    @TempDir
    Path tempDir;

    @BeforeEach
    void setUp() {
    	fileLoader = new ExternalFileLoader();
    }
    
    @Test
    @DisplayName("loadFile should load file")
    void testLoadExternalFile() throws IOException {
    	
    	Path emptyFile = tempDir.resolve("test.txt");
        Files.write(emptyFile, "# #".getBytes());
        List<String> lignes = fileLoader.loadData(emptyFile.toString());

        assertFalse(lignes.isEmpty());
        assertEquals("# #", lignes.get(0));
    }

    @Test
    @DisplayName("loadFile should handle empty file")
    void testLoadEmptyFile() throws IOException {
        Path emptyFile = tempDir.resolve("empty.txt");
        Files.write(emptyFile, "".getBytes());
        
        List<String> lines = fileLoader.loadData(emptyFile.toString());
        
        assertTrue(lines.isEmpty());
    }

    @Test
    @DisplayName("loadFile should handle file with empty lines")
    void testLoadFileWithEmptyLines() throws IOException {
        String contentWithEmptyLines = "# #\n\n## ";
        Path testFile = tempDir.resolve("test_empty_lines.txt");
        Files.write(testFile, contentWithEmptyLines.getBytes());
        
        List<String> lines = fileLoader.loadData(testFile.toString());
        
        assertEquals(3, lines.size());
        assertEquals("# #", lines.get(0));
        assertEquals("", lines.get(1));
        assertEquals("## ", lines.get(2));

    }

    @Test
    @DisplayName("loadFile should throw exception for non-existent file")
    void testLoadFileNotFound() {
        String nonExistentFile = "non_existent_file.txt";
        
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            fileLoader.loadData(nonExistentFile);
        });
        
        assertTrue(exception.getMessage().contains("Fichier non trouvé"));
        assertTrue(exception.getMessage().contains(nonExistentFile));
    }
}