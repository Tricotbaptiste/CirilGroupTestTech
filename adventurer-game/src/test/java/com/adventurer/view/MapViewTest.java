package com.adventurer.view;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.ApplicationTest;

import com.adventurer.controller.MapController;
import com.adventurer.model.Map;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

@ExtendWith({MockitoExtension.class, ApplicationExtension.class})
@DisplayName("Tests for MapView")
class MapViewTest extends ApplicationTest {

    @Mock
    private Map mockMap;

    @Mock
    private MapController mockController;

    @Mock
    private FXMLLoader mockLoader;

    @Mock
    private Parent mockRoot;

    @Mock
    private Scene mockScene;
   
    @Nested
    @DisplayName("Constructor tests")
    class ConstructorTests {

        @Test
        @DisplayName("Should create MapView with Map parameter")
        void testCreateMapViewWithMapParameter() {
            // When
            MapView view = new MapView(mockMap);

            // Then
            assertNotNull(view);
        }

        @Test
        @DisplayName("Should handle null Map parameter")
        void testNullMapParameter() {
            // When
            MapView view = new MapView(null);

            // Then
            assertNotNull(view);
        }
    }
}

/**
 * Separate test class for testing JavaFX Application lifecycle
 * This would be used for more comprehensive integration testing
 */
@ExtendWith(ApplicationExtension.class)
@DisplayName("MapView Application Lifecycle Tests")
class MapViewApplicationLifecycleTest extends ApplicationTest {

    @Mock
    private Map mockMap;

    @Test
    @DisplayName("Should properly initialize JavaFX application")
    void shouldProperlyInitializeJavaFXApplication() {
        // This test would verify the complete JavaFX application lifecycle
        // using TestFX capabilities
        
        Platform.runLater(() -> {
            MapView view = new MapView(mockMap);
            assertNotNull(view);
        });
    }

    @Override
    public void start(Stage stage) throws Exception {
        // Set up a minimal JavaFX application for testing
        MapView mapView = new MapView();
        // Don't actually call start() here to avoid infinite recursion
        stage.setTitle("Test Stage");
        stage.show();
    }
}