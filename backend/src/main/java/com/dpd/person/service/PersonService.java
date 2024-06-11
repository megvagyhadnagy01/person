package com.dpd.person.service;

import com.dpd.person.model.Person;
import com.dpd.person.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    public List<Person> getAllPersons() {
        return personRepository.findAll();
    }

    public Person createPerson(Person person) {
        return personRepository.save(person);
    }

    public Person updatePerson(Long id, Person personDetails) {
        Person person = personRepository.findById(id).orElseThrow(() -> new RuntimeException("Person not found"));
        person.setName(personDetails.getName());
        person.setBirthDate(personDetails.getBirthDate());
        person.setBirthPlace(personDetails.getBirthPlace());
        person.setMotherMaidenName(personDetails.getMotherMaidenName());
        person.setSocialSecurityNumber(personDetails.getSocialSecurityNumber());
        person.setTaxId(personDetails.getTaxId());
        person.setEmail(personDetails.getEmail());
        return personRepository.save(person);
    }

    public void deletePerson(Long id) {
        personRepository.deleteById(id);
    }

    public Person depersonalizePerson(Long id) {
        Person person = personRepository.findById(id).orElseThrow(() -> new RuntimeException("Person not found"));
        person.setName(null);
        person.setBirthDate(null);
        person.setBirthPlace(null);
        person.setMotherMaidenName(null);
        person.setSocialSecurityNumber(null);
        person.setTaxId(null);
        person.setEmail(null);
        return personRepository.save(person);
    }
}
