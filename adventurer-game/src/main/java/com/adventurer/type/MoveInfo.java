package com.adventurer.type;

import java.util.ArrayList;
import java.util.List;

/**
 * Regroup the data related to moving on the map
 */
public class MoveInfo {
	private int initialLineIndex;
	private int initialColumnIndex;
	
	private List<Direction> moves = new ArrayList<Direction>();
	
	public void setInitialLineIndex(int initialLineIndex) {
		this.initialLineIndex = initialLineIndex;
	}
	
	public void setInitialColumnIndex(int initialColumnIndex) {
		this.initialColumnIndex = initialColumnIndex;
	}
	
	public int getInitialLineIndex() {
		return this.initialLineIndex;
	}
	public int getInitialColumnIndex() {
		return this.initialColumnIndex;
	}
	
	public void addDirection(Direction d) {
		this.moves.add(d);
	}
	
	public List<Direction> getMoves() {
		return this.moves;
	}
}
