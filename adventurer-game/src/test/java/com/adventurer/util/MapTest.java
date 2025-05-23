package com.adventurer.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import com.adventurer.exception.InvalidMapCharacterException;
import com.adventurer.model.Map;
import com.adventurer.type.CellType;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("Map Tests")
class MapTest {

    private Map map;
    private CellType[][] mockGrid;
    private MapLoader mockMapLoader;

    @BeforeEach
    void setUp() {
        // Création d'une grille de test
        mockGrid = new CellType[3][4];
        mockGrid[0] = new CellType[]{CellType.FREE, CellType.WOOD, CellType.FREE, CellType.FREE};
        mockGrid[1] = new CellType[]{CellType.WOOD, CellType.FREE, CellType.WOOD, CellType.FREE};
        mockGrid[2] = new CellType[]{CellType.FREE, CellType.FREE, CellType.FREE, CellType.WOOD};
        
        mockMapLoader = mock(MapLoader.class);
    }

    @Test
    @DisplayName("Constructor should initialize map correctly")
    void testConstructor() throws InvalidMapCharacterException {
        when(mockMapLoader.loadMap()).thenReturn(mockGrid);
            
        map = new Map(mockMapLoader);
         
        assertEquals(3, map.getHeight());
        assertEquals(4, map.getWidth());
    }

    @Test
    @DisplayName("getCellType should return correct case type")
    void testgetCellType() throws InvalidMapCharacterException {
    	when(mockMapLoader.loadMap()).thenReturn(mockGrid);
        
        map = new Map(mockMapLoader);
                        
        assertEquals(CellType.FREE, map.getCellType(0, 0));
        assertEquals(CellType.WOOD, map.getCellType(1, 0));
        assertEquals(CellType.FREE, map.getCellType(2, 0));
        assertEquals(CellType.WOOD, map.getCellType(0, 1));
        assertEquals(CellType.WOOD, map.getCellType(2, 3));
    }
}