package com.etnetera.hr.data.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDate;


/**
 * Simple data entity describing basic properties of every JavaScript framework.
 *
 * @author Etnetera
 *
 */
@Data
public class JavaScriptFrameworkDto {

    private Long id;

    @NotEmpty
    @Size(max = 30)
    private String name;

    @NotEmpty
    @Size(max = 10)
    private String version;

    private LocalDate deprecationDate;

    private Integer hypeLevel;

    public JavaScriptFrameworkDto() {
    }

    public JavaScriptFrameworkDto(Long id, String name, String version, LocalDate deprecationDate, Integer hypeLevel) {
        this.id = id;
        this.name = name;
        this.version = version;
        this.deprecationDate = deprecationDate;
        this.hypeLevel = hypeLevel;
    }
}
