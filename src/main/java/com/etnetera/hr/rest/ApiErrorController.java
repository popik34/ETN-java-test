package com.etnetera.hr.rest;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.context.request.WebRequest;

/**
 * Main REST controller.
 * 
 * @author Etnetera
 *
 */
@RestControllerAdvice
public class ApiErrorController {

	@ResponseBody
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ApiError> handleValidationException(MethodArgumentNotValidException ex) {

		List<ValidationError> validationErrorList = ex.getBindingResult().getFieldErrors().stream().map(e -> new ValidationError(e.getField(), e.getCode())).collect(Collectors.toList());
		ApiError error = new ApiError();

		error.setStatus(HttpStatus.BAD_REQUEST);
		error.setError(ex.getClass().getName());
		error.setValidationErrors(validationErrorList);

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}

	@ResponseBody
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler({JavaScriptFrameworkNotFoundException.class, JavaScriptFrameworkBadRequestException.class})
	public ResponseEntity<ApiError> handleNotFoundException(JavaScriptFrameworkNotFoundException ex) {

		ApiError error = new ApiError();

		error.setStatus(HttpStatus.BAD_REQUEST);
		error.setError(ex.getClass().getName());

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}
}
