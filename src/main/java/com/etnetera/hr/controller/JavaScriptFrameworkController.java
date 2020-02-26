package com.etnetera.hr.controller;

import com.etnetera.hr.data.dto.JavaScriptFrameworkDto;
import com.etnetera.hr.service.JavaScriptFrameworkService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Simple REST controller for accessing application logic.
 * 
 * @author Etnetera
 *
 */
@RestController
public class JavaScriptFrameworkController {

	private final JavaScriptFrameworkService service;

	@Autowired
	public JavaScriptFrameworkController(JavaScriptFrameworkService service) {
		this.service = service;
	}

	@GetMapping("/frameworks")
	public ResponseEntity<CollectionModel<EntityModel<JavaScriptFrameworkDto>>> selectAllJavaScriptFramework(
			@Valid @RequestParam(name = "name", required = false) String name) {

		return service.selectAllJavaScriptFramework(name);
	}

	@GetMapping("/frameworks/{id}")
	public ResponseEntity<CollectionModel<EntityModel<JavaScriptFrameworkDto>>> selectByIdJavaScriptFramework(
			@PathVariable Long id) {

		return service.selectByIdJavaScriptFramework(id);
	}


	@PostMapping("/frameworks")
	ResponseEntity<EntityModel<JavaScriptFrameworkDto>> insertFramework(
			@Valid @RequestBody JavaScriptFrameworkDto frameworkDto) {

		return service.insertJavaScriptFramework(frameworkDto);
	}

	@PutMapping("/frameworks/{id}")
	ResponseEntity<EntityModel<JavaScriptFrameworkDto>> updateFramework(
			@RequestBody @Valid JavaScriptFrameworkDto frameworkDto,
			@PathVariable Long id) {

		return service.updateJavaScriptFramework(id, frameworkDto);
	}

	@DeleteMapping("/frameworks/{id}")
	ResponseEntity<?> deleteFramework(
			@PathVariable Long id) {

		return service.deleteJavaScriptFramework(id);
	}
}
