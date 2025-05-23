package com.adventurer.view;

import java.io.IOException;
import java.net.URISyntaxException;

import com.adventurer.controller.MapController;
import com.adventurer.exception.InvalidDirectionException;
import com.adventurer.exception.InvalidInitialPositionException;
import com.adventurer.model.Map;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * JavaFX application that manages map rendering
 * @see Application
 */
public class MapView extends Application {

	private static final String PATH_TO_FXML = "/view/map-view.fxml";
    private static Map map;

    public MapView(Map map) {
    	MapView.map = map; 
    }
    
    public MapView() {}
    
    @Override
    public void start(Stage primaryStage) throws IOException, URISyntaxException, InvalidDirectionException, InvalidInitialPositionException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(PATH_TO_FXML));

        Parent root = loader.load();

        MapController controller = loader.getController();

        controller.initData(MapView.map);
        
        Scene scene = new Scene(root);
        primaryStage.setTitle("Aventurier - Simulation");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void launchApp() {
        launch();
    }
}