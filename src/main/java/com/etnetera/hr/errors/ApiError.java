package com.etnetera.hr.errors;

import lombok.Data;

import java.util.List;

/**
 * 
 * Envelope for the validation errors. Represents JSON response.
 * 
 * @author Etnetera
 *
 */
@Data
public class ApiError {

	private List<ValidationError> validationErrors;

	private int status;

	private String error;

	private String message;
}
