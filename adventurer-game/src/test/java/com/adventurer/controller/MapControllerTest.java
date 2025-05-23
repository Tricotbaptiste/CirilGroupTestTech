package com.adventurer.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.adventurer.model.Map;
import com.adventurer.type.CellType;
import com.adventurer.type.Direction;
import com.adventurer.type.MoveInfo;
import com.adventurer.util.MoveLoader;

@ExtendWith(MockitoExtension.class)
class MapControllerTest {

    @Mock
    private Map mockMap;
    
    @Mock
    private MoveInfo mockMoveInfo;

    @Mock
    private MoveLoader moveLoader;
    
    private MapController mapController;

    @BeforeEach
    void setUp() {
        mapController = new MapController();
    }

    @Test
    @DisplayName("Valid position should return true")
    void testIsPositionValid_Valid() {
        // Given
        when(mockMap.getHeight()).thenReturn(5);
        when(mockMap.getWidth()).thenReturn(5);
        when(mockMap.getCellType(2, 2)).thenReturn(CellType.FREE);
        
        setPrivateField(mapController, "map", mockMap);
        
        // When
        boolean result = mapController.isPositionValid(2, 2);
        
        // Then
        assertTrue(result);
    }

    @Test
    @DisplayName("Negativ position should return false")
    void testIsPositionValid_NegativXPosition() {
        // Given
    	lenient().when(mockMap.getHeight()).thenReturn(5);
        
        setPrivateField(mapController, "map", mockMap);
        
        boolean resultX = mapController.isPositionValid(-1, 2);
        assertFalse(resultX);

    }
    
    @Test
    @DisplayName("Negativ position should return false")
    void testIsPositionValid_NegativYPosition() {
        // Given
    	lenient().when(mockMap.getWidth()).thenReturn(5);
        
        setPrivateField(mapController, "map", mockMap);
        
        boolean resultY = mapController.isPositionValid(1, -2);
        assertFalse(resultY);

    }

    @Test
    @DisplayName("Out of bounds position should return false")
    void testIsPositionValid_Out() {
        // Given
        when(mockMap.getHeight()).thenReturn(5);
        when(mockMap.getWidth()).thenReturn(5);
        
        setPrivateField(mapController, "map", mockMap);
        
        boolean resultX = mapController.isPositionValid(5, 2);
        assertFalse(resultX);
        boolean resultY = mapController.isPositionValid(2, 5);
        assertFalse(resultY);
    }

    @Test
    @DisplayName("Good position but with woods should return false")
    void testIsPositionValid_WithWood() {
        // Given
        when(mockMap.getHeight()).thenReturn(5);
        when(mockMap.getWidth()).thenReturn(5);
        when(mockMap.getCellType(2, 2)).thenReturn(CellType.WOOD);
        
        setPrivateField(mapController, "map", mockMap);
        
        // When
        boolean result = mapController.isPositionValid(2, 2);
        
        // Then
        assertFalse(result);
    }

    @Test
    @DisplayName("All correct moves should be added to the list")
    void testCleanMoves_WithValidMoves() {
        // Given
        when(mockMap.getHeight()).thenReturn(5);
        when(mockMap.getWidth()).thenReturn(5);
        when(mockMap.getCellType(anyInt(), anyInt())).thenReturn(CellType.FREE);
        when(mockMoveInfo.getInitialLineIndex()).thenReturn(2);
        when(mockMoveInfo.getInitialColumnIndex()).thenReturn(2);
        when(mockMoveInfo.getMoves()).thenReturn(Arrays.asList(Direction.N, Direction.E));
        
        setPrivateField(mapController, "map", mockMap);
        
        // When
        mapController.cleanMoves(mockMoveInfo);
        
        // Then
        @SuppressWarnings("unchecked")
        List<int[]> moves = (List<int[]>) getPrivateField(mapController, "moves");
        assertNotNull(moves);
        assertEquals(2, moves.size());
        
        // Vérifier le premier mouvement (Nord : y-1)
        assertEquals(1, moves.get(0)[0]); // x décrémenté
        assertEquals(2, moves.get(0)[1]); // y reste identique
        
        // Vérifier le deuxième mouvement (Est : x+1 par rapport à la nouvelle position)
        assertEquals(1, moves.get(1)[0]); // x reste identique à la position précédente
        assertEquals(3, moves.get(1)[1]); // y incrémenté
    }

    @Test
    @DisplayName("All incorrect moves should not be added to the list")
    void testCleanMoves_WithInvalidMoves() {
        // Given
        when(mockMap.getHeight()).thenReturn(3);
        when(mockMap.getWidth()).thenReturn(3);
        when(mockMap.getCellType(anyInt(), anyInt())).thenReturn(CellType.WOOD);
        when(mockMoveInfo.getInitialLineIndex()).thenReturn(1);
        when(mockMoveInfo.getInitialColumnIndex()).thenReturn(1);
        when(mockMoveInfo.getMoves()).thenReturn(Arrays.asList(Direction.N, Direction.E));
        
        setPrivateField(mapController, "map", mockMap);
        
        // When
        mapController.cleanMoves(mockMoveInfo);
        
        // Then
        @SuppressWarnings("unchecked")
        List<int[]> moves = (List<int[]>) getPrivateField(mapController, "moves");
        assertNotNull(moves);
        assertEquals(0, moves.size());
    }

    @Test
    @DisplayName("Check if a move to the north change the position correctly")
    void testCleanMoves_DirectionNorth_ShouldDecrementLine() {
        // Given
        when(mockMap.getHeight()).thenReturn(5);
        when(mockMap.getWidth()).thenReturn(5);
        when(mockMap.getCellType(anyInt(), anyInt())).thenReturn(CellType.FREE);
        when(mockMoveInfo.getInitialLineIndex()).thenReturn(2);
        when(mockMoveInfo.getInitialColumnIndex()).thenReturn(2);
        when(mockMoveInfo.getMoves()).thenReturn(Arrays.asList(Direction.N));
        
        setPrivateField(mapController, "map", mockMap);
        
        // When
        mapController.cleanMoves(mockMoveInfo);
        
        // Then
        @SuppressWarnings("unchecked")
        List<int[]> moves = (List<int[]>) getPrivateField(mapController, "moves");
        assertEquals(1, moves.size());
        assertEquals(1, moves.get(0)[0]);
        assertEquals(2, moves.get(0)[1]);
    }

    @Test
    @DisplayName("Check if a move to the south change the position correctly")
    void testCleanMoves_DirectionSouth_ShouldIncrementLine() {
        // Given
        when(mockMap.getHeight()).thenReturn(5);
        when(mockMap.getWidth()).thenReturn(5);
        when(mockMap.getCellType(anyInt(), anyInt())).thenReturn(CellType.FREE);
        when(mockMoveInfo.getInitialLineIndex()).thenReturn(2);
        when(mockMoveInfo.getInitialColumnIndex()).thenReturn(2);
        when(mockMoveInfo.getMoves()).thenReturn(Arrays.asList(Direction.S));
        
        setPrivateField(mapController, "map", mockMap);
        
        // When
        mapController.cleanMoves(mockMoveInfo);
        
        // Then
        @SuppressWarnings("unchecked")
        List<int[]> moves = (List<int[]>) getPrivateField(mapController, "moves");
        assertEquals(1, moves.size());
        assertEquals(3, moves.get(0)[0]);
        assertEquals(2, moves.get(0)[1]);
    }

    @Test
    @DisplayName("Check if a move to the east change the position correctly")
    void testCleanMoves_DirectionEast_ShouldIncrementColumn() {
        // Given
        when(mockMap.getHeight()).thenReturn(5);
        when(mockMap.getWidth()).thenReturn(5);
        when(mockMap.getCellType(anyInt(), anyInt())).thenReturn(CellType.FREE);
        when(mockMoveInfo.getInitialLineIndex()).thenReturn(2);
        when(mockMoveInfo.getInitialColumnIndex()).thenReturn(2);
        when(mockMoveInfo.getMoves()).thenReturn(Arrays.asList(Direction.E));
        
        setPrivateField(mapController, "map", mockMap);
        
        // When
        mapController.cleanMoves(mockMoveInfo);
        
        // Then
        @SuppressWarnings("unchecked")
        List<int[]> moves = (List<int[]>) getPrivateField(mapController, "moves");
        assertEquals(1, moves.size());
        assertEquals(2, moves.get(0)[0]);
        assertEquals(3, moves.get(0)[1]);
    }

    @Test
    @DisplayName("Check if a move to the west change the position correctly")
    void testCleanMoves_DirectionWest_ShouldDecrementColumn() {
        // Given
        when(mockMap.getHeight()).thenReturn(5);
        when(mockMap.getWidth()).thenReturn(5);
        when(mockMap.getCellType(anyInt(), anyInt())).thenReturn(CellType.FREE);
        when(mockMoveInfo.getInitialLineIndex()).thenReturn(2);
        when(mockMoveInfo.getInitialColumnIndex()).thenReturn(2);
        when(mockMoveInfo.getMoves()).thenReturn(Arrays.asList(Direction.O));
        
        setPrivateField(mapController, "map", mockMap);
        
        // When
        mapController.cleanMoves(mockMoveInfo);
        
        // Then
        @SuppressWarnings("unchecked")
        List<int[]> moves = (List<int[]>) getPrivateField(mapController, "moves");
        assertEquals(1, moves.size());
        assertEquals(2, moves.get(0)[0]);
        assertEquals(1, moves.get(0)[1]);
    }

    @Test
    @DisplayName("Only correct moves should be added to the list")
    void testCleanMoves_MixedValidAndInvalidMoves() {
        // Given
    	lenient().when(mockMap.getHeight()).thenReturn(5);
    	lenient().when(mockMap.getWidth()).thenReturn(5);
        // Position (1,1) libre, mais pas (0,1) ni (2,1)
    	lenient().when(mockMap.getCellType(1, 1)).thenReturn(CellType.FREE);
    	lenient().when(mockMap.getCellType(0, 1)).thenReturn(CellType.FREE);
        lenient().when(mockMap.getCellType(0, 2)).thenReturn(CellType.WOOD);
        lenient().when(mockMoveInfo.getInitialLineIndex()).thenReturn(1);
        lenient().when(mockMoveInfo.getInitialColumnIndex()).thenReturn(1);
        lenient().when(mockMoveInfo.getMoves()).thenReturn(Arrays.asList(Direction.N, Direction.E));
        
        setPrivateField(mapController, "map", mockMap);
        
        // When
        mapController.cleanMoves(mockMoveInfo);
        
        // Then
        @SuppressWarnings("unchecked")
        List<int[]> moves = (List<int[]>) getPrivateField(mapController, "moves");
        assertEquals(1, moves.size());
        assertEquals(0, moves.get(0)[0]);
        assertEquals(1, moves.get(0)[1]);
    }

    @Test
    @DisplayName("Empty moves list should create an empty list")
    void testCleanMoves_EmptyMovesList() {
        // Given
        when(mockMoveInfo.getInitialLineIndex()).thenReturn(2);
        when(mockMoveInfo.getInitialColumnIndex()).thenReturn(1);
        when(mockMoveInfo.getMoves()).thenReturn(Arrays.asList());
        
        setPrivateField(mapController, "map", mockMap);
        
        // When
        mapController.cleanMoves(mockMoveInfo);
        
        // Then
        @SuppressWarnings("unchecked")
        List<int[]> moves = (List<int[]>) getPrivateField(mapController, "moves");
        assertNotNull(moves);
        assertEquals(0, moves.size());
    }
    
    // Méthodes utilitaires pour l'accès aux champs privés
    private void setPrivateField(Object object, String fieldName, Object value) {
        try {
            java.lang.reflect.Field field = object.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(object, value);
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de l'injection du champ " + fieldName, e);
        }
    }

    private Object getPrivateField(Object object, String fieldName) {
        try {
            java.lang.reflect.Field field = object.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(object);
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la récupération du champ " + fieldName, e);
        }
    }
}