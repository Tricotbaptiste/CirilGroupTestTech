package com.adventurer.view;
import com.adventurer.type.CellType;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Factory for the different map cell type
 */
public class CellFactory {

    private static final int CELL_SIZE = 30;

	public static Rectangle createCell(CellType type) {
        Rectangle cell = new Rectangle(CELL_SIZE, CELL_SIZE);
        
        if (type == null) {
            cell.setFill(Color.LIGHTGRAY);
            cell.setStroke(Color.GRAY);
            return cell;
        }
        
        switch (type) {
            case FREE:
                cell.setFill(Color.WHITE);
                cell.setStroke(Color.LIGHTGRAY);
                break;
            case WOOD:
                cell.setFill(Color.DARKGREEN);
                cell.setStroke(Color.BLACK);
                break;
            case ADVENTURER:
            	cell.setFill(Color.RED);
            	cell.setStroke(Color.BLACK);
            	break;
            default:
                cell.setFill(Color.LIGHTGRAY);
                cell.setStroke(Color.GRAY);
        }
        
        return cell;
    }
}
