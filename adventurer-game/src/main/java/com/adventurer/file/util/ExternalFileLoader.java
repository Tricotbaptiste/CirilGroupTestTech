package com.adventurer.file.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URISyntaxException;

/**
 * Loads a file external to the application
 * @see FileLoader
 */
public class ExternalFileLoader extends FileLoader{

	@Override
	public InputStream loadFile(String filePath) {
		try {
			//Si filePath est un chemin absolu
			File directFile = new File(filePath);
		    if (directFile.exists() && directFile.isFile()) {
				return new FileInputStream(directFile);
		    }
		    
		    //Si filePath est un chemin relatif à partir du dossier du .jar
			String jarPath = new File(getClass().getProtectionDomain().getCodeSource().getLocation().toURI()).getParent();

			File fichierPresJar = new File(jarPath, filePath);
			if (fichierPresJar.exists() && fichierPresJar.isFile()) {
				return new FileInputStream(fichierPresJar);
			} 
		} catch (FileNotFoundException e) {
		    throw new RuntimeException("Fichier externe non trouvé : " + filePath);
		} catch (URISyntaxException e) {
			throw new RuntimeException("Erreur lors de la récupération du fichier externe : " + e.getMessage());
		}
        return null;
	}

}
