package com.etnetera.hr.rest;

import lombok.Data;

/**
 * 
 * Validation error. Represents JSON response.
 * 
 * @author Etnetera
 *
 */
@Data
public class ValidationError {

	private String field;
	private String message;

	public ValidationError() {
	}

	public ValidationError(String field, String message) {
		this.field = field;
		this.message = message;
	}

}
