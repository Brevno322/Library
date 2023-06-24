package ru.library.accounting.util;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import ru.library.accounting.dao.BookDAO;
import ru.library.accounting.model.Book;

@Component
public class BookValidation implements Validator {

    private final BookDAO bookDAO;
@Autowired
    public BookValidation(BookDAO bookDAO) {
        this.bookDAO = bookDAO;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Book.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {

    }
}
