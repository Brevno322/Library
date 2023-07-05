package ru.library.accounting.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.library.accounting.models.Book;
import ru.library.accounting.models.Person;
import ru.library.accounting.repositories.BookRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BookService {

    private final BookRepository bookRepository;


    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }


    public List<Book> showAllBooks() {
        return bookRepository.findAll();
    }


    public Book showBook(int id) {
        Optional<Book> bookFound = bookRepository.findById(id);
        return bookFound.orElse(null);
    }

    public List<Book> showBookByIdPerson(int id) {
        return bookRepository.showBookByIdPerson(id);
    }


    @Transactional
    public void updateBook(int id, Book book) {
        book.setId(id);
        bookRepository.save(book);
    }

    @Transactional
    public void saveBook(Book book) {
        bookRepository.save(book);
    }

    //    @Transactional
//    public void orderBook(int id, Person person) {
//        bookRepository.orderBook(id, person);
//    }
    @Transactional
    public void orderBook(int id, Person person, Book book) {
        book.setId(id);
        book.setPerson(person);
        book.setDataOfIssue(new Date());
        bookRepository.save(book);
    }


    @Transactional
    public void returnBook(int id) {
        Book book = bookRepository.findById(id).orElse(null);
        assert book != null;
        book.setPerson(null);
        bookRepository.save(book);

    }

    @Transactional
    public void deleteBook(int id) {
        bookRepository.deleteById(id);
    }


}
