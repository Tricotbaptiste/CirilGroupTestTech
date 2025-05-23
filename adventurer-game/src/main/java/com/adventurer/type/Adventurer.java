package com.adventurer.type;

/**
 * Represent the player on the map
 */
public class Adventurer {
	private int lineIndex;
	private int columnIndex;
	
	public Adventurer(int lineIndex, int columnIndex) {
		this.lineIndex = lineIndex;
		this.columnIndex = columnIndex;
	}
	
	public void move(int[] newCoor) {
		this.lineIndex = newCoor[0];
		this.columnIndex = newCoor[1];
	}
	
	public int getLineIndex() {
        return lineIndex;
    }

    public int getColumnIndex() {
        return columnIndex;
    }
}
