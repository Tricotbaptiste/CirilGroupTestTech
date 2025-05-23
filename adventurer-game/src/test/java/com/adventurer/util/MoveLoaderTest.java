package com.adventurer.util;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.adventurer.exception.InvalidDirectionException;
import com.adventurer.file.util.ExternalFileLoader;
import com.adventurer.file.util.FileLoader;
import com.adventurer.type.MoveInfo;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests for MoveLoader")
class MoveLoaderTest {

    private FileLoader fileLoader;

    private MoveLoader moveLoader;

    @BeforeEach
    void setUp() {
    	fileLoader = mock(ExternalFileLoader.class);
        moveLoader = new MoveLoader();
    }

        @Test
        @DisplayName("Should successfully load valid move data")
        void testLoadValidMoveData() throws IOException, InvalidDirectionException {
            // Given
            List<String> validLines = Arrays.asList("5,3", "NSEO");
            lenient().doReturn(validLines).when(fileLoader).loadData("");
            // When
            MoveInfo result = moveLoader.load(fileLoader);

            // Then
            assertNotNull(result);
        }

        @Test
        @DisplayName("Should throw IOException when FileLoader throws IOException")
        void testFileLoaderFails() throws IOException {
            // Given
        	lenient().when(fileLoader.loadData("moves.txt"))
                .thenThrow(new IOException("File not found"));

            // When & Then
            IOException exception = assertThrows(IOException.class, 
                () -> moveLoader.load(fileLoader));
        }

        @Test
        @DisplayName("Should throw IOException for null moves")
        void testNullMoves() {
            // When & Then
            IOException exception = assertThrows(IOException.class, 
                () -> moveLoader.loadMoves(null));
            
            assertEquals("Aucun déplacements définis dans le fichier", exception.getMessage());
        }

        @Test
        @DisplayName("Should throw IOException for empty moves")
        void testEmptyMoves() {
            // When & Then
            IOException exception = assertThrows(IOException.class, 
                () -> moveLoader.loadMoves(""));
            
            assertEquals("Aucun déplacements définis dans le fichier", exception.getMessage());
        }

        @Test
        @DisplayName("Should throw InvalidDirectionException for invalid direction character")
        void testLoadMovesInvalidCharacter() {
            // Given
            String invalidMoves = "NSEZ"; // Z is not a valid direction

            // When & Then
            InvalidDirectionException exception = assertThrows(InvalidDirectionException.class, 
                () -> moveLoader.loadMoves(invalidMoves));
        }

        @Test
        @DisplayName("Should process each valid direction character")
        void testLoadMovesValidDirectionCharacter() throws InvalidDirectionException, IOException {
            // Given
            String moves = "NSEO";
            
            // When
            assertEquals(4, moveLoader.loadMoves(moves).getMoves().size());
        }
}