package com.adventurer.exception;

/**
 *  Exception thrown when the initial position retrieved is invalid
 */
public class InvalidInitialPositionException extends Exception{

	public InvalidInitialPositionException(String position) {
		super("Position initiale invalide :" + position);
	}
}