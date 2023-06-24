package ru.library.accounting.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.library.accounting.dao.PersonDAO;
import ru.library.accounting.model.Person;

@Component
public class PersonValidation implements Validator {

    private final PersonDAO personDAO;

    @Autowired
    public PersonValidation(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Person.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {

    }
}
