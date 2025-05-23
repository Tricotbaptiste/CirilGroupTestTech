package com.adventurer.view;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.adventurer.type.CellType;

import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("CellFactory Tests")
class CellFactoryTest {

    private static final int CELL_SIZE = 30;

    @BeforeAll
    static void initToolkit() {
        try {
            javafx.application.Platform.startup(() -> {});
        } catch (IllegalStateException e) {
        }
    }

    @Test
    @DisplayName("createCell should create correct FREE cell")
    void testCreateFreeCell() {
        Rectangle cell = CellFactory.createCell(CellType.FREE);
        
        assertNotNull(cell);
        assertEquals(CELL_SIZE, cell.getWidth());
        assertEquals(CELL_SIZE, cell.getHeight());
        assertEquals(Color.WHITE, cell.getFill());
        assertEquals(Color.LIGHTGRAY, cell.getStroke());
    }

    @Test
    @DisplayName("createCell should create correct WOOD cell")
    void testCreateWoodCell() {
        Rectangle cell = CellFactory.createCell(CellType.WOOD);
        
        assertNotNull(cell);
        assertEquals(CELL_SIZE, cell.getWidth());
        assertEquals(CELL_SIZE, cell.getHeight());
        assertEquals(Color.DARKGREEN, cell.getFill());
        assertEquals(Color.BLACK, cell.getStroke());
    }

    @Test
    @DisplayName("createCell should create correct ADVENTURER cell")
    void testCreateAdventurerCell() {
        Rectangle cell = CellFactory.createCell(CellType.ADVENTURER);
        
        assertNotNull(cell);
        assertEquals(CELL_SIZE, cell.getWidth());
        assertEquals(CELL_SIZE, cell.getHeight());
        assertEquals(Color.RED, cell.getFill());
        assertEquals(Color.BLACK, cell.getStroke());
    }

    @Test
    @DisplayName("createCell should handle null case type with default style")
    void testCreateCellWithNull() {
        Rectangle cell = CellFactory.createCell(null);
        
        assertNotNull(cell);
        assertEquals(CELL_SIZE, cell.getWidth());
        assertEquals(CELL_SIZE, cell.getHeight());
        assertEquals(Color.LIGHTGRAY, cell.getFill());
        assertEquals(Color.GRAY, cell.getStroke());
    }

    @Test
    @DisplayName("createCell should be static and accessible")
    void testStaticAccess() {
        // Test que la méthode est bien statique
        Rectangle cell = CellFactory.createCell(CellType.FREE);
        assertNotNull(cell);
        
        // On peut l'appeler sans instancier la classe
        Rectangle anotherCell = CellFactory.createCell(CellType.WOOD);
        assertNotNull(anotherCell);
    }
}