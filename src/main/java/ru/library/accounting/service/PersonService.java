package ru.library.accounting.service;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.library.accounting.models.Book;
import ru.library.accounting.models.Person;
import ru.library.accounting.repositories.PersonRepository;

import java.util.*;

@Service
@Transactional(readOnly = true)
public class PersonService {

    private final PersonRepository personRepository;


    @Autowired
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;

    }


    public List<Person> index() {
        return personRepository.findAll();
    }


    public Person showPersonById(int id) {

        Optional<Person> foundPerson = personRepository.findById(id);
        return foundPerson.orElse(null);
    }


    public List<Book> getBooksByPersonId(int id) {
        Optional<Person> person = personRepository.findById(id);

        if (person.isPresent()) {
            Hibernate.initialize(person.get().getBooks());


            person.get().getBooks().forEach(book -> {
                long diffInMillies = Math.abs(book.getDataOfIssue().getTime() - new Date().getTime());

                if (diffInMillies > 864000000)
                    book.setExpired(true);
            });

            return person.get().getBooks();
        } else {
            return Collections.emptyList();
        }
    }

    public Optional<Person> getPersonByFullName(String name) {
        return personRepository.findPersonByFullName(name);
    }

    @Transactional
    public void updatePerson(int id, Person person) {
        person.setId(id);
        personRepository.save(person);
    }

    @Transactional
    public void savePerson(Person person) {
        personRepository.save(person);
    }

    @Transactional
    public void deletePerson(int id) {
        personRepository.deleteById(id);
    }


}
