package com.etnetera.hr.service;

import com.etnetera.hr.controller.JavaScriptFrameworkController;
import com.etnetera.hr.data.dto.JavaScriptFrameworkDto;
import com.etnetera.hr.data.entity.JavaScriptFramework;
import com.etnetera.hr.repository.JavaScriptFrameworkRepository;
import com.etnetera.hr.rest.JavaScriptFrameworkBadRequestException;
import com.etnetera.hr.rest.JavaScriptFrameworkNotFoundException;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class JavaScriptFrameworkServiceImpl implements JavaScriptFrameworkService {

    private final JavaScriptFrameworkRepository repository;

    public JavaScriptFrameworkServiceImpl(JavaScriptFrameworkRepository repository) {
        this.repository = repository;
    }

    @Override
    public ResponseEntity<CollectionModel<EntityModel<JavaScriptFrameworkDto>>> selectByIdJavaScriptFramework(Long id) {

        if(!repository.existsById(id)) {
            throw new JavaScriptFrameworkNotFoundException();
        }

        List<EntityModel<JavaScriptFrameworkDto>> entityModelList = repository.findById(id).stream()
                .map(this::assemblyToEntityModel)
                .collect(Collectors.toList());

        return ResponseEntity.ok(this.assemblyToCollectionModel(entityModelList));
    }

    @Override
    public ResponseEntity<CollectionModel<EntityModel<JavaScriptFrameworkDto>>> selectAllJavaScriptFramework(String name) {

        List<EntityModel<JavaScriptFrameworkDto>> entityModelList;

        if(StringUtils.isEmpty(name)) {
            entityModelList = repository.findAll().stream()
                    .map(this::assemblyToEntityModel)
                    .collect(Collectors.toList());
        } else {
            entityModelList = repository.findByName(name).stream()
                    .map(this::assemblyToEntityModel)
                    .collect(Collectors.toList());
        }

        return ResponseEntity.ok(this.assemblyToCollectionModel(entityModelList));
    }

    @Override
    public ResponseEntity<EntityModel<JavaScriptFrameworkDto>> insertJavaScriptFramework(JavaScriptFrameworkDto frameworkDto) {

        if (repository.existsById(frameworkDto.getId())) {
            throw new JavaScriptFrameworkBadRequestException();
        }

        EntityModel<JavaScriptFrameworkDto> entityModel = this.assemblyToEntityModel(
                repository.save(this.convertDtoToEntity(new JavaScriptFramework(), frameworkDto)));

        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    @Override
    public ResponseEntity<EntityModel<JavaScriptFrameworkDto>> updateJavaScriptFramework(Long id, JavaScriptFrameworkDto frameworkDto) {

        EntityModel<JavaScriptFrameworkDto> entityModel = this.assemblyToEntityModel(
                repository.findById(id)
                        .map(frameworkEntity -> {
                            //save dto into existing entity
                            frameworkDto.setId(frameworkEntity.getId());
                            return repository.save(this.convertDtoToEntity(frameworkEntity, frameworkDto));
                        }).orElseGet(() -> {
                            //save dto into new entity
                            frameworkDto.setId(id);
                            return repository.save( this.convertDtoToEntity(new JavaScriptFramework(), frameworkDto));
                        }));

        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    @Override
    public ResponseEntity<?> deleteJavaScriptFramework(Long id) {

        if(!repository.existsById(id)) {
            throw new JavaScriptFrameworkNotFoundException();
        }

        repository.deleteById(id);

        return ResponseEntity.noContent().build();
    }

    private EntityModel<JavaScriptFrameworkDto> assemblyToEntityModel(JavaScriptFramework entity) {
        JavaScriptFrameworkDto dto = convertEntityToDto(entity, new JavaScriptFrameworkDto());
        return new EntityModel<>(
                dto,
                linkTo(methodOn(JavaScriptFrameworkController.class).selectByIdJavaScriptFramework(dto.getId())).withSelfRel(),
                linkTo(methodOn(JavaScriptFrameworkController.class).selectAllJavaScriptFramework(null)).withRel("frameworks"));
    }

    private CollectionModel<EntityModel<JavaScriptFrameworkDto>> assemblyToCollectionModel(List<EntityModel<JavaScriptFrameworkDto>> entityModelList) {

        return new CollectionModel<>(entityModelList, linkTo(methodOn(JavaScriptFrameworkController.class).selectAllJavaScriptFramework(null)).withSelfRel());
    }

    private JavaScriptFrameworkDto convertEntityToDto(JavaScriptFramework entity, JavaScriptFrameworkDto dto) {
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setVersion(entity.getVersion());
        dto.setDeprecationDate(entity.getDeprecationDate());
        dto.setHypeLevel(entity.getHypeLevel());
        return dto;
    }

    private JavaScriptFramework convertDtoToEntity(JavaScriptFramework entity, JavaScriptFrameworkDto dto) {

        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setVersion(dto.getVersion());
        entity.setDeprecationDate(dto.getDeprecationDate());
        entity.setHypeLevel(dto.getHypeLevel());
        return entity;
    }
}
