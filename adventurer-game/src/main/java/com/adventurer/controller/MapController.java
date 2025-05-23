package com.adventurer.controller;

import com.adventurer.exception.InvalidDirectionException;
import com.adventurer.exception.InvalidInitialPositionException;
import com.adventurer.file.util.ExternalFileLoader;
import com.adventurer.model.Map;
import com.adventurer.type.Adventurer;
import com.adventurer.type.CellType;
import com.adventurer.type.Direction;
import com.adventurer.type.MoveInfo;
import com.adventurer.util.MoveLoader;
import com.adventurer.view.CellFactory;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * View controller, manages the display and updating of elements
 */
public class MapController implements Initializable{
	
    @FXML
    private ScrollPane mapScrollPane;
    
    @FXML
    private Button nextMoveBtn;
    
    @FXML
    private Button playAllBtn;
    
    @FXML
    private Button pauseBtn;
    
    @FXML
    private Button resetBtn;
    
    @FXML
    private Slider speedSlider;
    
    private GridPane gridPane;
    private Timeline animationTimeline;
    
    private Map map;
    private Adventurer adventurer;
    private MoveInfo moveInfos;
    private List<int[]> moves = new ArrayList<int[]>();
    private int currentMoveIndex = 0;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Initialisation du GridPane pour la map
        gridPane = new GridPane();
        gridPane.setHgap(1);
        gridPane.setVgap(1);
        mapScrollPane.setContent(gridPane);
        
        // Configuration des écouteurs pour les boutons
        nextMoveBtn.setOnAction(e -> executeNextMove());
        playAllBtn.setOnAction(e -> playAllMoves());
        pauseBtn.setOnAction(e -> pauseAnimation());
        resetBtn.setOnAction(e -> resetGame());
        
        // Écouteur pour le slider de vitesse
        speedSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (animationTimeline != null) {
                playAllMoves();
            }
        });
    }
    
    /**
     * Initialiaze the model data
     * @throws IOException 
     * @throws URISyntaxException 
     * @throws InvalidDirectionException 
     * @throws InvalidInitialPositionException 
     */
    public void initData(Map map) throws IOException, URISyntaxException, InvalidDirectionException, InvalidInitialPositionException {
        this.map = map;
        MoveLoader moveLoader = new MoveLoader();
        this.moveInfos = moveLoader.load(new ExternalFileLoader());
        if (this.isPositionValid(moveInfos.getInitialLineIndex(), moveInfos.getInitialColumnIndex())) {
        	this.adventurer = new Adventurer(moveInfos.getInitialLineIndex(), moveInfos.getInitialColumnIndex());
            this.cleanMoves(moveInfos);
            
            renderMap();
        }
        else {
        	throw new InvalidInitialPositionException(String.valueOf(this.moveInfos.getInitialLineIndex()) + ";" + String.valueOf(this.moveInfos.getInitialColumnIndex()));
        }
        
    }
    
    /**
     * Display the map's elements
     */
    private void renderMap() {
        gridPane.getChildren().clear();

        for (int line = 0; line < map.getHeight(); line++) {
            for (int column = 0; column < map.getWidth(); column++) {
                CellType type = map.getCellType(line, column);
                Rectangle cell;
                if( line == adventurer.getLineIndex() && column == adventurer.getColumnIndex()) {
                	cell = CellFactory.createCell(CellType.ADVENTURER);
                } else {
                	cell = CellFactory.createCell(type);
                }
                gridPane.add(cell, column, line);
            }
        }
    }

    /**
     * Execute the next move in the list
     */
    private void executeNextMove() {
        if (currentMoveIndex < moves.size()) {
            int[] newCoord = moves.get(currentMoveIndex);

            adventurer.move(newCoord);
            
            currentMoveIndex++;
            renderMap();
            
            if (currentMoveIndex >= moves.size()) {
                nextMoveBtn.setDisable(true);
                playAllBtn.setDisable(true);
                pauseBtn.setDisable(true);
            }
        }
    }
    
    /**
     * Start an animation that execute everymove in the list
     */
    private void playAllMoves() {
        if (currentMoveIndex >= moves.size()) {
            return;
        }
        
        pauseAnimation();
        
        // Calculer la durée en fonction de la vitesse
        double speed = speedSlider.getValue();
        Duration frameDuration = Duration.seconds(1.0 / speed);
        
        animationTimeline = new Timeline();
        animationTimeline.setCycleCount(moves.size() - currentMoveIndex);
        
        KeyFrame keyFrame = new KeyFrame(frameDuration, event -> executeNextMove());
        animationTimeline.getKeyFrames().add(keyFrame);
        
        //désactiver les boutons manuels
        nextMoveBtn.setDisable(true);
        playAllBtn.setDisable(true);
        pauseBtn.setDisable(false);
        
        animationTimeline.setOnFinished(e -> {
            pauseBtn.setDisable(true);
        });
        
        animationTimeline.play();
    }
    
    /**
     * Stop the animation 
     */
    private void pauseAnimation() {
        if (animationTimeline != null) {
            animationTimeline.stop();
            animationTimeline = null;
            
            nextMoveBtn.setDisable(false);
            playAllBtn.setDisable(false);
            pauseBtn.setDisable(true);
        }
    }

    /**
     * Resets positions and motion playback animation
     */
    private void resetGame() {
        pauseAnimation();
        
        currentMoveIndex = 0;
        adventurer.move(new int[] {this.moveInfos.getInitialLineIndex(), this.moveInfos.getInitialColumnIndex()});
        
        renderMap();
        
        nextMoveBtn.setDisable(false);
        playAllBtn.setDisable(false);
    }
    
    /**
     * Sort movements so that only possible movements remain
     */
    public void cleanMoves(MoveInfo moveInfo) {
    	List<Direction> moves = moveInfo.getMoves();
    	int line = moveInfo.getInitialLineIndex();
    	int column = moveInfo.getInitialColumnIndex();
    	
    	int newLine = line;
    	int newColumn = column;
    	for(Direction d : moves) {
    		newLine = line;
    		newColumn = column;
    		switch(d) {
    			case N:
    				newLine--;
    				break;
    			case S:
    				newLine++;
    				break;
    			case E:
    				newColumn++;
    				break;
    			case O:
    				newColumn--;
    				break;
    			default:
    		}

    		if (isPositionValid(newLine, newColumn)) {
    			this.moves.add(new int[] {newLine, newColumn});
    			line = newLine;
    			column = newColumn;
    		}
    	}
    	
    }	
   
    /**
     * True if the cell is valid and free
     */
    public boolean isPositionValid(int line, int column) {
    	if (line < 0 || line >= this.map.getHeight() || column < 0 || column >= this.map.getWidth()) {
            return false;
        }
        return this.map.getCellType(line, column) == CellType.FREE;
    }
}
