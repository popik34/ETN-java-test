package com.etnetera.hr.errors;

public class JavaScriptFrameworkNotFoundException extends RuntimeException {

    public JavaScriptFrameworkNotFoundException(Long id) {

        super("Not Found framework with ID: " + id);
    }
}
