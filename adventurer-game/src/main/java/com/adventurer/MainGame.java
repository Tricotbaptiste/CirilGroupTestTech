package com.adventurer;


import com.adventurer.file.util.ResourcesFileLoader;
import com.adventurer.model.Map;
import com.adventurer.util.AppContext;
import com.adventurer.util.FileMapLoader;
import com.adventurer.view.MapView;

/**
 * Main entrance for the game
 */
public class MainGame {
	
	public static void main(String[] args) {
		if (args.length != 1) {
            System.err.println("Usage: java -jar aventurier.jar <fichier_deplacements>");
            System.exit(1);
        }
		AppContext.movesFile = args[0];
		Map map = new Map(new FileMapLoader(new ResourcesFileLoader()));
        MapView mapView = new MapView(map);
        
        mapView.launchApp();
	}

}
