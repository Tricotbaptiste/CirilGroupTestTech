package com.adventurer.file.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * abstract class for the class that load a txt file
 */
public abstract class FileLoader {
	
	public FileLoader() {}
	
	/**
	 * Read and return the lines from a txt file
	 * @param filePath
	 * @throws IOException
	 */
	public List<String> loadData(String filePath) throws IOException {
		List<String> lines = new ArrayList<>();
		InputStream inputStream = loadFile(filePath);
		
		if (inputStream == null) {
	        throw new RuntimeException("Fichier non trouvé : " + filePath);
	    }
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
        	String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
            return lines;
        }
	}
	
	/**
	 * 
	 * @param filePath
	 * @return InputStream
	 */
	public abstract InputStream loadFile(String filePath);
}
