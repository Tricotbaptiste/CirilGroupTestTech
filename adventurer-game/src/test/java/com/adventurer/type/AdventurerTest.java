package com.adventurer.type;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Adventurer Tests")
class AdventurerTest {

    private Adventurer adventurer;

    @BeforeEach
    void setUp() {
        adventurer = new Adventurer(5, 3);
    }

    @Test
    @DisplayName("Constructor should set initial position correctly")
    void testConstructor() {
        assertEquals(5, adventurer.getLineIndex());
        assertEquals(3, adventurer.getColumnIndex());
    }

    @Test
    @DisplayName("Move should update position correctly")
    void testMove() {
        int[] newPosition = {7, 9};
        adventurer.move(newPosition);
        
        assertEquals(7, adventurer.getLineIndex());
        assertEquals(9, adventurer.getColumnIndex());
    }

    @Test
    @DisplayName("Move with zero coordinates")
    void testMoveToOrigin() {
        int[] origin = {0, 0};
        adventurer.move(origin);
        
        assertEquals(0, adventurer.getLineIndex());
        assertEquals(0, adventurer.getColumnIndex());
    }

    @Test
    @DisplayName("Multiple moves should work correctly")
    void testMultipleMoves() {
    	int[] firstMove = {2, 4};
        adventurer.move(firstMove);
        assertEquals(2, adventurer.getLineIndex());
        assertEquals(4, adventurer.getColumnIndex());
        
        int[] secondMove = {8, 1};
        adventurer.move(secondMove);
        assertEquals(8, adventurer.getLineIndex());
        assertEquals(1, adventurer.getColumnIndex());
    }

    @Test
    @DisplayName("Get methods should return current position")
    void testGetMethods() {
        assertEquals(5, adventurer.getLineIndex());
        assertEquals(3, adventurer.getColumnIndex());
        
        // Après un déplacement
        adventurer.move(new int[]{10, 15});
        assertEquals(10, adventurer.getLineIndex());
        assertEquals(15, adventurer.getColumnIndex());
    }
}