package com.etnetera.hr;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.etnetera.hr.data.entity.JavaScriptFramework;
import com.etnetera.hr.repository.JavaScriptFrameworkRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDate;


/**
 * Class used for Spring Boot/MVC based tests.
 * 
 * @author Etnetera
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class JavaScriptFrameworkTests {

	@Autowired
	private MockMvc mockMvc;
	
	private ObjectMapper mapper = new ObjectMapper();

	@Autowired
	private JavaScriptFrameworkRepository repository;

	private void prepareData() {
		repository.deleteAll();

		JavaScriptFramework react = new JavaScriptFramework("ReactJS", "7.0.0", LocalDate.of(2030, 1, 1), 100);
		JavaScriptFramework vue = new JavaScriptFramework("Vue.js", "9.8.1", LocalDate.of(2015, 1, 1), -5);
		
		repository.save(react);
		repository.save(vue);
	}

	@Test
	public void frameworksGetAllValid() throws Exception {
		prepareData();

		mockMvc.perform(get("/frameworks")).andExpect(status().isOk()).andExpect(content().contentType(MediaTypes.HAL_JSON))
				.andExpect(jsonPath("$._links", hasKey("self")))
				.andExpect(jsonPath("_embedded.javaScriptFrameworkDtoList", hasSize(2)))
				.andExpect(jsonPath("_embedded.javaScriptFrameworkDtoList[0].name", is("ReactJS")))
				.andExpect(jsonPath("_embedded.javaScriptFrameworkDtoList[0].version", is("7.0.0")))
				.andExpect(jsonPath("_embedded.javaScriptFrameworkDtoList[0].deprecationDate", is("2030-01-01")))
				.andExpect(jsonPath("_embedded.javaScriptFrameworkDtoList[0].hypeLevel", is(100)))
				.andExpect(jsonPath("_embedded.javaScriptFrameworkDtoList[0]._links", hasKey("self")))
				.andExpect(jsonPath("_embedded.javaScriptFrameworkDtoList[0]._links", hasKey("frameworks")))
				.andExpect(jsonPath("_embedded.javaScriptFrameworkDtoList[1].name", is("Vue.js")))
				.andExpect(jsonPath("_embedded.javaScriptFrameworkDtoList[1].version", is("9.8.1")))
				.andExpect(jsonPath("_embedded.javaScriptFrameworkDtoList[1].deprecationDate", is("2015-01-01")))
				.andExpect(jsonPath("_embedded.javaScriptFrameworkDtoList[1].hypeLevel", is(-5)))
				.andExpect(jsonPath("_embedded.javaScriptFrameworkDtoList[1]._links", hasKey("self")))
				.andExpect(jsonPath("_embedded.javaScriptFrameworkDtoList[1]._links", hasKey("frameworks")));

		mockMvc.perform(get("/frameworks?name=ReactJS")).andExpect(status().isOk()).andExpect(content().contentType(MediaTypes.HAL_JSON))
				.andExpect(jsonPath("$._links", hasKey("self")))
				.andExpect(jsonPath("_embedded.javaScriptFrameworkDtoList", hasSize(1)))
				.andExpect(jsonPath("_embedded.javaScriptFrameworkDtoList[0].name", is("ReactJS")))
				.andExpect(jsonPath("_embedded.javaScriptFrameworkDtoList[0].version", is("7.0.0")))
				.andExpect(jsonPath("_embedded.javaScriptFrameworkDtoList[0].deprecationDate", is("2030-01-01")))
				.andExpect(jsonPath("_embedded.javaScriptFrameworkDtoList[0].hypeLevel", is(100)))
				.andExpect(jsonPath("_embedded.javaScriptFrameworkDtoList[0]._links", hasKey("self")))
				.andExpect(jsonPath("_embedded.javaScriptFrameworkDtoList[0]._links", hasKey("frameworks")));
	}

	@Test
	public void frameworksGetByIdValid() throws Exception {
		prepareData();

		mockMvc.perform(get("/frameworks/1")).andExpect(status().isOk()).andExpect(content().contentType(MediaTypes.HAL_JSON))
				.andExpect(jsonPath("_embedded.javaScriptFrameworkDtoList", hasSize(1)))
				.andExpect(jsonPath("_embedded.javaScriptFrameworkDtoList[0].id", is(1)))
				.andExpect(jsonPath("_embedded.javaScriptFrameworkDtoList[0].name", is("ReactJS")))
				.andExpect(jsonPath("_embedded.javaScriptFrameworkDtoList[0].version", is("7.0.0")))
				.andExpect(jsonPath("_embedded.javaScriptFrameworkDtoList[0].deprecationDate", is("2030-01-01")))
				.andExpect(jsonPath("_embedded.javaScriptFrameworkDtoList[0].hypeLevel", is(100)))
				.andExpect(jsonPath("_embedded.javaScriptFrameworkDtoList[0]._links", hasKey("self")))
				.andExpect(jsonPath("_embedded.javaScriptFrameworkDtoList[0]._links", hasKey("frameworks")));
	}

	@Test
	public void frameworksGetByIdInvalid() throws Exception {
		prepareData();

		mockMvc.perform(get("/frameworks/98")).andExpect(status().isBadRequest()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.status", is(400)))
				.andExpect(jsonPath("$.error", is("com.etnetera.hr.errors.JavaScriptFrameworkNotFoundException")))
				.andExpect(jsonPath("$.message", notNullValue()))
				.andExpect(jsonPath("$.validationErrors", nullValue()));

	}
	
	@Test
	public void frameworksPostInvalid() throws Exception {
		prepareData();

		JavaScriptFramework framework = new JavaScriptFramework();
		mockMvc.perform(post("/frameworks").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsBytes(framework)))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.status", is(400)))
				.andExpect(jsonPath("$.error", is("org.springframework.web.bind.MethodArgumentNotValidException")))
				.andExpect(jsonPath("$.message", notNullValue()))
				.andExpect(jsonPath("$.validationErrors", hasSize(2)))
				.andExpect(jsonPath("$.validationErrors[0].message", is("NotEmpty")))
				.andExpect(jsonPath("$.validationErrors[1].message", is("NotEmpty")));

		
		framework.setName("verylongnameofthejavascriptframeworkjavaisthebest");
		framework.setVersion("versionversionversion");
		mockMvc.perform(post("/frameworks").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsBytes(framework)))
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.validationErrors", hasSize(2)))
			.andExpect(jsonPath("$.validationErrors[0].message", is("Size")))
			.andExpect(jsonPath("$.validationErrors[1].message", is("Size")));
	}

	@Test
	public void frameworksPostValid() throws Exception {
		prepareData();

		JavaScriptFramework framework = new JavaScriptFramework();

		framework.setName("testframework");
		framework.setVersion("1.0.0");
		framework.setHypeLevel(80);

		mockMvc.perform(post("/frameworks").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsBytes(framework)))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.id", notNullValue()))
				.andExpect(jsonPath("$.name", is("testframework")))
				.andExpect(jsonPath("$.version", is("1.0.0")))
				.andExpect(jsonPath("$.hypeLevel", is(80)));
	}

	@Test
	public void frameworksPutValid() throws Exception {
		prepareData();

		JavaScriptFramework framework = new JavaScriptFramework();

		framework.setName("testframework");
		framework.setVersion("1.0.0");
		framework.setHypeLevel(80);

		mockMvc.perform(put("/frameworks/2").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsBytes(framework)))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.id", is(2)))
				.andExpect(jsonPath("$.name", is("testframework")))
				.andExpect(jsonPath("$.version", is("1.0.0")))
				.andExpect(jsonPath("$.hypeLevel", is(80)));
	}

	@Test
	public void frameworksDeleteInvalid() throws Exception {
		prepareData();

		mockMvc.perform(delete("/frameworks/87"))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.status", is(400)))
				.andExpect(jsonPath("$.error", is("com.etnetera.hr.errors.JavaScriptFrameworkNotFoundException")))
				.andExpect(jsonPath("$.message", notNullValue()));
	}

	@Test
	public void frameworksDeleteValid() throws Exception {
		prepareData();

		mockMvc.perform(delete("/frameworks/2"))
				.andExpect(status().isNoContent());
	}
	
}
