package com.adventurer.model;

import com.adventurer.exception.InvalidMapCharacterException;
import com.adventurer.type.CellType;
import com.adventurer.util.MapLoader;

/**
 * Model that contains the grid
 */
public class Map {
	    
    private CellType[][] grid;
    private int height;
    private int width;

    public Map(MapLoader maploader) {
        CellType[][] grid;
		try {
			grid = maploader.loadMap();
			this.grid = grid;
	        this.height = grid.length;
	        this.width = grid[0].length;
		} catch (InvalidMapCharacterException e) {
			e.printStackTrace();
		}
    }
    
    public int getHeight() {
    	return this.height;
    }
    
    public int getWidth() {
    	return this.width;
    }

	public CellType getCellType(int line, int column) {
		return this.grid[line][column];
	}
}
