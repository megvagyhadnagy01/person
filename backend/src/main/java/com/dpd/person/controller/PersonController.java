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
            logger.info("Person created successfully: {}", savedPerson);
            return ResponseEntity.ok(savedPerson);
        } catch (Exception e) {
            logger.error("Error creating person", e);
            return ResponseEntity.status(400).body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePerson(@PathVariable Long id, @Valid @RequestBody Person personDetails) {
        logger.info("Received request to update person with id: {}", id);

        return personRepository.findById(id).map(person -> {
            person.setName(personDetails.getName());
            person.setBirthDate(personDetails.getBirthDate());
            person.setBirthPlace(personDetails.getBirthPlace());
            person.setMotherMaidenName(personDetails.getMotherMaidenName());
            person.setSocialSecurityNumber(personDetails.getSocialSecurityNumber());
            person.setTaxId(personDetails.getTaxId());
            person.setEmail(personDetails.getEmail());

            // Frissítjük a címeket és telefonszámokat
            person.getAddresses().clear();
            person.getAddresses().addAll(personDetails.getAddresses());
            person.getAddresses().forEach(address -> address.setPerson(person));

            person.getPhoneNumbers().clear();
            person.getPhoneNumbers().addAll(personDetails.getPhoneNumbers());
            person.getPhoneNumbers().forEach(phoneNumber -> phoneNumber.setPerson(person));

            try {
                Person updatedPerson = personRepository.save(person);
                logger.info("Person updated successfully: {}", updatedPerson);
                return ResponseEntity.ok(updatedPerson);
            } catch (Exception e) {
                logger.error("Error updating person", e);
                return ResponseEntity.status(500).body(null);
            }
        }).orElseThrow(() -> {
            logger.error("Person not found for this id :: {}", id);
            return new ResourceNotFoundException("Person not found for this id :: " + id);
        });
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Boolean>> deletePerson(@PathVariable Long id) {
        logger.info("Received request to delete person with id: {}", id);

        return personRepository.findById(id).map(person -> {
            try {
                personRepository.delete(person);
                Map<String, Boolean> response = new HashMap<>();
                response.put("deleted", Boolean.TRUE);
                logger.info("Person deleted successfully: {}", id);
                return ResponseEntity.ok(response);
            } catch (Exception e) {
                logger.error("Error deleting person", e);
                Map<String, Boolean> response = new HashMap<>();
                response.put("deleted", Boolean.FALSE);
                return ResponseEntity.status(500).body(response);
            }
        }).orElseThrow(() -> {
            logger.error("Person not found for this id :: {}", id);
            return new ResourceNotFoundException("Person not found for this id :: " + id);
        });
    }
}
