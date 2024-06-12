package com.dpd.person.controller;

import com.dpd.person.exception.ResourceNotFoundException;
import com.dpd.person.model.Person;
import com.dpd.person.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/persons")
@Validated
public class PersonController {

    private static final Logger logger = LoggerFactory.getLogger(PersonController.class);

    @Autowired
    private PersonRepository personRepository;

    @GetMapping
    public List<Person> getAllPersons() {
        return personRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<Person> createPerson(@Valid @RequestBody Person person) {
        logger.info("Received request to create person: {}", person);

        // Beállítjuk a person mezőt az address és phoneNumber entitásokban
        person.getAddresses().forEach(address -> address.setPerson(person));
        person.getPhoneNumbers().forEach(phoneNumber -> phoneNumber.setPerson(person));

        try {
            Person savedPerson = personRepository.save(person);
            return ResponseEntity.ok(savedPerson);
        } catch (Exception e) {
            logger.error("Error creating person", e);
            return ResponseEntity.status(400).body(null);
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<Person> updatePerson(@PathVariable Long id, @Valid @RequestBody Person personDetails) {
        try {
            Person person = personRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Person not found for this id :: " + id));

            person.setName(personDetails.getName());
            person.setBirthDate(personDetails.getBirthDate());
            person.setBirthPlace(personDetails.getBirthPlace());
            person.setMotherMaidenName(personDetails.getMotherMaidenName());
            person.setSocialSecurityNumber(personDetails.getSocialSecurityNumber());
            person.setTaxId(personDetails.getTaxId());
            person.setEmail(personDetails.getEmail());
            person.setAddresses(personDetails.getAddresses());
            person.setPhoneNumbers(personDetails.getPhoneNumbers());

            final Person updatedPerson = personRepository.save(person);
            return ResponseEntity.ok(updatedPerson);
        } catch (Exception e) {
            logger.error("Error updating person", e);
            return ResponseEntity.status(500).body(null);
        }
    }

    @DeleteMapping("/{id}")
    public Map<String, Boolean> deletePerson(@PathVariable Long id) {
        try {
            Person person = personRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Person not found for this id :: " + id));

            personRepository.delete(person);
            Map<String, Boolean> response = new HashMap<>();
            response.put("deleted", Boolean.TRUE);
            return response;
        } catch (Exception e) {
            logger.error("Error deleting person", e);
            Map<String, Boolean> response = new HashMap<>();
            response.put("deleted", Boolean.FALSE);
            return response;
        }
    }
}
