package ru.library.accounting.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.library.accounting.models.Person;
import ru.library.accounting.repositories.PersonRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PersonService {

    private final PersonRepository personRepository;

    private final BookService bookService;

    @Autowired
    public PersonService(PersonRepository personRepository, BookService bookService) {
        this.personRepository = personRepository;
        this.bookService = bookService;
    }


    public List<Person> index() {
        return personRepository.findAll();
    }


    public Person showPersonById(int id) {

        Optional<Person> foundPerson = personRepository.findById(id);
        return foundPerson.orElse(null);
    }

    @Transactional
    public Optional<Person> showPersonByIdBook(int id) {
        return personRepository.findBookByPersonId(id);
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
