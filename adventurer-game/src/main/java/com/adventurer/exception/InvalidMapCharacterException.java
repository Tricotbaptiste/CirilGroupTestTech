package com.adventurer.exception;

/**
 * Exception thrown when a map character is invalid
 */
public class InvalidMapCharacterException extends Exception{
	public InvalidMapCharacterException(String character) {
		super("Caractère invalide de la carte : " + character);
	}
}
