package com.adventurer.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.adventurer.exception.InvalidMapCharacterException;
import com.adventurer.file.util.FileLoader;
import com.adventurer.type.CellType;

/**
 * Loads a map from a file
 * @see MapLoader
 */
public class FileMapLoader implements MapLoader {
	
	private static final String MAP_FILE = "game/map.txt";
	private FileLoader fileLoader;
	
	public FileMapLoader(FileLoader fileLoader) {
		this.fileLoader = fileLoader;
	};
	
	public CellType[][] loadMap() throws InvalidMapCharacterException {
		
		List<String> lines = new ArrayList<String>();
		try {
			lines = fileLoader.loadData(MAP_FILE);
		} catch (IOException e) {
			throw new RuntimeException("Erreur lors de la lecture du fichier de map: " + e.getMessage());
		}
        
        int height = lines.size();
        int width = height != 0 ? lines.get(0).length() : 0;
        CellType[][] map = new CellType[height][width];
        
        for (int x=0; x<height; x++) {
        	String line = lines.get(x);
        	for (int y = 0; y < line.length(); y++) {
                switch(line.charAt(y)) {
                	case '#':
                		map[x][y] = CellType.WOOD;
                		break;
                	case ' ':
                		map[x][y] = CellType.FREE;
                		break;
                	default:
                		throw new InvalidMapCharacterException(String.valueOf(line.charAt(y)));             
                }
            }
        }
        
        return map;
	}
}
