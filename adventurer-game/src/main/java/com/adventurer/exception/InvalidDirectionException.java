package com.adventurer.exception;

/**
 * Exception thrown when a retrieved direction is invalid
 */
public class InvalidDirectionException extends Exception{

	public InvalidDirectionException(String direction) {
		super("direction invalide :" + direction);
	}
}
