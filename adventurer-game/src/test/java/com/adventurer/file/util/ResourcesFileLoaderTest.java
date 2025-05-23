package com.adventurer.file.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.io.TempDir;

import java.io.*;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("ResourcesFileLoader Tests")
class ResourcesFileLoaderTest {

    private FileLoader fileLoader;
    
    @TempDir
    Path tempDir;

    @BeforeEach
    void setUp() {
    	fileLoader = new ResourcesFileLoader();
    }
    @Test
    @DisplayName("loadFile should load file from resources")
    void testLoadFileFromResources() throws IOException {
        List<String> lines = fileLoader.loadData("test.txt");

        assertFalse(lines.isEmpty());
        assertEquals("# #", lines.get(0));
    }

    @Test
    @DisplayName("loadFile should handle empty file")
    void testLoadEmptyFile() throws IOException {
        
    	List<String> lines = fileLoader.loadData("test-empty.txt");        
        assertTrue(lines.isEmpty());
    }

    @Test
    @DisplayName("loadFile should handle file with empty lines")
    void testLoadFileWithEmptyLines() throws IOException {
        
        List<String> lines = fileLoader.loadData("test-empty-lines.txt"); 
        
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