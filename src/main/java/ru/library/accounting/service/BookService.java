package ru.library.accounting.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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


    public List<Book> showAllBooks(boolean sortBy) {
        if (sortBy) {
            return bookRepository.findAll(Sort.by("year"));
        } else
            return bookRepository.findAll();
    }

    public List<Book> findWithPagination(Integer page, Integer booksPerPage, boolean sortByYear) {
        if (sortByYear) {
            return bookRepository.findAll(PageRequest.of(page, booksPerPage, Sort.by("year"))).getContent();
        } else
            return bookRepository.findAll(PageRequest.of(page, booksPerPage)).getContent();
    }


    public Book showBook(int id) {
        Optional<Book> bookFound = bookRepository.findById(id);
        return bookFound.orElse(null);
    }

    public List<Book> searchBook(String name) {
        return bookRepository.findBookByNameStartingWith(name);
    }

    @Transactional
    public void updateBook(int id, Book updateBook) {
        Book bookToBeUpdate=bookRepository.findById(id).get();
        updateBook.setId(id);
        updateBook.setPerson(bookToBeUpdate.getPerson());
        bookRepository.save(updateBook);
    }

    @Transactional
    public void saveBook(Book book) {
        bookRepository.save(book);
    }


    @Transactional
    public void orderBook(int id, Person person) {
        bookRepository.findById(id).ifPresent(book -> {
            book.setDataOfIssue(new Date());
            book.setPerson(person);
        });

    }


    @Transactional
    public void returnBook(int id) {
        bookRepository.findById(id).ifPresent(book -> {
            book.setPerson(null);
            book.setDataOfIssue(null);
        });
    }

    public Person getBookOwner(int id) {
        return bookRepository.findById(id).map(Book::getPerson).orElse(null);
    }


    @Transactional
    public void deleteBook(int id) {
        bookRepository.deleteById(id);
    }


}
