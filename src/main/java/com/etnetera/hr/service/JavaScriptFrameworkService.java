package com.etnetera.hr.service;

import com.etnetera.hr.data.dto.JavaScriptFrameworkDto;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;

public interface JavaScriptFrameworkService {

    ResponseEntity<CollectionModel<EntityModel<JavaScriptFrameworkDto>>> selectByIdJavaScriptFramework(Long id);

    ResponseEntity<CollectionModel<EntityModel<JavaScriptFrameworkDto>>> selectAllJavaScriptFramework(String name);

    ResponseEntity<EntityModel<JavaScriptFrameworkDto>> insertJavaScriptFramework(JavaScriptFrameworkDto dto);

    ResponseEntity<EntityModel<JavaScriptFrameworkDto>> updateJavaScriptFramework(Long id, JavaScriptFrameworkDto dto);

    ResponseEntity<?> deleteJavaScriptFramework(Long id);

}
