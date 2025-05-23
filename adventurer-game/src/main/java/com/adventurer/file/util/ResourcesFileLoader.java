package com.adventurer.file.util;

import java.io.InputStream;

/**
 * Loads a file internal to the application
 * @see FileLoader
 */
public class ResourcesFileLoader extends FileLoader{

	public ResourcesFileLoader() {}
	@Override
	public InputStream loadFile(String filePath) {
		return getClass().getClassLoader().getResourceAsStream(filePath);
	}

}
