package com.adventurer.util;

import com.adventurer.exception.InvalidMapCharacterException;
import com.adventurer.type.CellType;

/**
 * Interface for the different map loaders
 */
public interface MapLoader {
	public CellType[][] loadMap() throws InvalidMapCharacterException;
}
