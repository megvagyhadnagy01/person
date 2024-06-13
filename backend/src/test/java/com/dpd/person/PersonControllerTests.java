package com.dpd.person;


import com.dpd.person.controller.PersonController;
import com.dpd.person.model.Person;
import com.dpd.person.repository.PersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PersonController.class)
public class PersonControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private PersonRepository personRepository;

	private Person person;

	@BeforeEach
	public void setUp() throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date birthDate = sdf.parse("1980-01-01");

		person = new Person();
		person.setId(1L);
		person.setName("John Doe");
		person.setBirthDate(birthDate); // Set birthDate as Date
		person.setBirthPlace("New York");
		person.setMotherMaidenName("Jane Smith");
		person.setSocialSecurityNumber("123456789");
		person.setTaxId("9876543210");
		person.setEmail("john.doe@example.com");
		person.setAddresses(Collections.emptyList());
		person.setPhoneNumbers(Collections.emptyList());
	}

	@Test
	public void testGetAllPersons() throws Exception {
		Mockito.when(personRepository.findAll()).thenReturn(Collections.singletonList(person));

		mockMvc.perform(get("/api/persons"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].name", is(person.getName())));
	}

	@Test
	public void testCreatePerson() throws Exception {
		Mockito.when(personRepository.save(Mockito.any(Person.class))).thenReturn(person);

		mockMvc.perform(post("/api/persons")
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\"name\": \"John Doe\", \"birthDate\": \"1980-01-01\", \"birthPlace\": \"New York\", \"motherMaidenName\": \"Jane Smith\", \"socialSecurityNumber\": \"123456789\", \"taxId\": \"9876543210\", \"email\": \"john.doe@example.com\", \"addresses\":[], \"phoneNumbers\":[]}"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name", is(person.getName())));
	}

	@Test
	public void testUpdatePerson() throws Exception {
		Mockito.when(personRepository.findById(1L)).thenReturn(Optional.of(person));
		Mockito.when(personRepository.save(Mockito.any(Person.class))).thenReturn(person);

		mockMvc.perform(put("/api/persons/1")
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\"name\": \"John Doe Updated\", \"birthDate\": \"1980-01-01\", \"birthPlace\": \"New York\", \"motherMaidenName\": \"Jane Smith\", \"socialSecurityNumber\": \"123456789\", \"taxId\": \"9876543210\", \"email\": \"john.doe@example.com\", \"addresses\":[], \"phoneNumbers\":[]}"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name", is("John Doe Updated")));
	}

	@Test
	public void testDeletePerson() throws Exception {
		Mockito.when(personRepository.findById(1L)).thenReturn(Optional.of(person));
		Mockito.doNothing().when(personRepository).delete(person);

		mockMvc.perform(delete("/api/persons/1"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.deleted", is(true)));
	}
}
