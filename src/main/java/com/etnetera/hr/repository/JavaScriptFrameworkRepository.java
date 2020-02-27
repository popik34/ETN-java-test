package com.etnetera.hr.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.etnetera.hr.data.entity.JavaScriptFramework;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Spring data repository interface used for accessing the data in database.
 * 
 * @author Etnetera
 *
 */
@Component
public interface JavaScriptFrameworkRepository extends JpaRepository<JavaScriptFramework, Long> {
    List<JavaScriptFramework> findByName(String name);
}
