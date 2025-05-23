package com.adventurer.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.adventurer.exception.InvalidDirectionException;
import com.adventurer.file.util.FileLoader;
import com.adventurer.type.Direction;
import com.adventurer.type.MoveInfo;

/**
 * Loads the moves from an txt file
 */
public class MoveLoader {
	
	private MoveInfo moveInfo;

	public MoveLoader() {}
	
	public MoveInfo load(FileLoader fileloader) throws IOException, InvalidDirectionException{
		moveInfo = new MoveInfo();
		List<String> lines = new ArrayList<String>();
		
		try {
			lines = fileloader.loadData(AppContext.movesFile);
		} catch (IOException e) {
			throw new IOException("Erreur lors de la lecture du fichier de déplacements: " + e.getMessage());
		}
		if (lines.isEmpty() || lines.get(0).split(",").length != 2) {
            throw new IOException("Format de position initiale invalide: 2");
        }
		
		String[] position = lines.get(0).split(",");
		
        moveInfo.setInitialLineIndex(Integer.parseInt(position[1]));
        moveInfo.setInitialColumnIndex(Integer.parseInt(position[0]));
        
		for (Direction d : loadMoves(lines.get(1)).getMoves()) {
			moveInfo.addDirection(d);
		}
		
		return moveInfo;
	}
	
	public MoveInfo loadMoves(String line) throws InvalidDirectionException, IOException {
		MoveInfo moveInfo = new MoveInfo();
        if (line == null || line.isEmpty()) {
            throw new IOException("Aucun déplacements définis dans le fichier");
        }
        
        for (char c : line.toCharArray()) {
        	try {
        		moveInfo.addDirection(Direction.valueOf(String.valueOf(c)));
        	} catch (IllegalArgumentException e) {
        		throw new InvalidDirectionException(String.valueOf(c)); 
        	}
        } 
        return moveInfo;
	}

	
}
