package com.etnetera.hr.rest;

import lombok.Data;
import org.springframework.http.HttpStatus;

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

	private HttpStatus status;

	private String error;
}
