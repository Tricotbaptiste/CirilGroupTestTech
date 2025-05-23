package com.adventurer.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import com.adventurer.exception.InvalidMapCharacterException;
import com.adventurer.file.util.ResourcesFileLoader;
import com.adventurer.type.CellType;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("MapLoader Tests")
class FileMapLoaderTest {

    private ResourcesFileLoader mockFileLoader;
    private FileMapLoader fileMapLoader;

    @BeforeEach
    void setUp() {
        mockFileLoader = mock(ResourcesFileLoader.class);
        fileMapLoader = new FileMapLoader(mockFileLoader);
    }

    @Test
    @DisplayName("loadMap should create correct map from valid input")
    void testLoadMapValidInput() throws IOException, InvalidMapCharacterException {
        List<String> testLines = Arrays.asList(
            "# #",
            " # ",
            "   "
        );
        
        when(mockFileLoader.loadData(anyString())).thenReturn(testLines);
        
        CellType[][] result = fileMapLoader.loadMap();
        
        assertNotNull(result);
        assertEquals(3, result.length); // height
        assertEquals(3, result[0].length); // width
        
        // Vérifier le contenu
        assertEquals(CellType.WOOD, result[0][0]);
        assertEquals(CellType.FREE, result[0][1]);
        assertEquals(CellType.WOOD, result[0][2]);
        
        assertEquals(CellType.FREE, result[1][0]);
        assertEquals(CellType.WOOD, result[1][1]);
        assertEquals(CellType.FREE, result[1][2]);
        
        assertEquals(CellType.FREE, result[2][0]);
        assertEquals(CellType.FREE, result[2][1]);
        assertEquals(CellType.FREE, result[2][2]);
    }

    @Test
    @DisplayName("loadMap should handle single line map")
    void testLoadMapSingleLine() throws IOException, InvalidMapCharacterException {
        List<String> testLines = Collections.singletonList("# #");
        
        when(mockFileLoader.loadData(anyString())).thenReturn(testLines);
        
        CellType[][] result = fileMapLoader.loadMap();
        
        assertNotNull(result);
        assertEquals(1, result.length);
        assertEquals(3, result[0].length);
        
        assertEquals(CellType.WOOD, result[0][0]);
        assertEquals(CellType.FREE, result[0][1]);
        assertEquals(CellType.WOOD, result[0][2]);
    }

    @Test
    @DisplayName("loadMap should handle single column map")
    void testLoadMapSingleColumn() throws IOException, InvalidMapCharacterException {
        List<String> testLines = Arrays.asList("#", " ", "#");
        
        when(mockFileLoader.loadData(anyString())).thenReturn(testLines);
        
        CellType[][] result = fileMapLoader.loadMap();
        
        assertNotNull(result);
        assertEquals(3, result.length);
        assertEquals(1, result[0].length);
        
        assertEquals(CellType.WOOD, result[0][0]);
        assertEquals(CellType.FREE, result[1][0]);
        assertEquals(CellType.WOOD, result[2][0]);
    }

    @Test
    @DisplayName("loadMap should handle empty map")
    void testLoadMapEmpty() throws IOException, InvalidMapCharacterException {
        List<String> emptyLines = Collections.emptyList();
        
        when(mockFileLoader.loadData(anyString())).thenReturn(emptyLines);
        
        CellType[][] result = fileMapLoader.loadMap();
        
        assertNotNull(result);
        assertEquals(0, result.length);
    }

    @Test
    @DisplayName("loadMap should handle map with only spaces")
    void testLoadMapOnlySpaces() throws IOException, InvalidMapCharacterException {
        List<String> testLines = Arrays.asList(
            "   ",
            "   ",
            "   "
        );
        
        when(mockFileLoader.loadData(anyString())).thenReturn(testLines);
        
        CellType[][] result = fileMapLoader.loadMap();
        
        assertNotNull(result);
        assertEquals(3, result.length);
        assertEquals(3, result[0].length);
        
        // Toutes les cases doivent être FREE
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                assertEquals(CellType.FREE, result[i][j]);
            }
        }
    }

    @Test
    @DisplayName("loadMap should handle IOException gracefully")
    void testLoadMapIOException() throws IOException {
        when(mockFileLoader.loadData(anyString())).thenThrow(new IOException("fichier incorrect"));
        
        RuntimeException exception = assertThrows(RuntimeException.class, fileMapLoader::loadMap);
        assertTrue(exception.getMessage().contains("Erreur lors de la lecture du fichier de map"));
    }
}