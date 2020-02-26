package com.etnetera.hr.data.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;


/**
 * Simple data entity describing basic properties of every JavaScript framework.
 * 
 * @author Etnetera
 *
 */
@Data
@Entity
@Table(name = "JAVASCRIPT_FRAMEWORK")
public class JavaScriptFramework {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(nullable = false, length = 30)
	private String name;

	@Column(nullable = false, length = 10)
	private String version;

	@Column
	private LocalDate deprecationDate;

	@Column
	private Integer hypeLevel;

	public JavaScriptFramework() {
	}

	public JavaScriptFramework(String name, String version, LocalDate deprecationDate, Integer hypeLevel) {
		this.name = name;
		this.version = version;
		this.deprecationDate = deprecationDate;
		this.hypeLevel = hypeLevel;
	}
}
