package com.etraveli.exceptions;

public class InvalidRequestException extends RuntimeException {
    
	private static final long serialVersionUID = 5712756544390969228L;

	public InvalidRequestException(String message) {
        super(message);
    }
}
