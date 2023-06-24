package ru.library.accounting.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.library.accounting.dao.BookDAO;
import ru.library.accounting.dao.PersonDAO;
import ru.library.accounting.model.Book;
import ru.library.accounting.model.Person;
import ru.library.accounting.util.BookValidation;
import ru.library.accounting.util.PersonValidation;

import java.util.Optional;

@Controller
@RequestMapping("/books")
public class BookController {
    private final PersonDAO personDAO;
    private final PersonValidation personValidation;
    private final BookDAO bookDAO;
    private final BookValidation bookValidation;

    @Autowired
    public BookController(PersonDAO personDAO, PersonValidation personValidation, BookDAO bookDAO, BookValidation bookValidation) {
        this.personDAO = personDAO;
        this.personValidation = personValidation;
        this.bookDAO = bookDAO;
        this.bookValidation = bookValidation;
    }

    @GetMapping("/new")
    public String createBook(@ModelAttribute("book") Book book) {
        return "book/newBook";
    }

    @PostMapping("/new")
    public String newBook(@ModelAttribute("book") Book book) {
        bookDAO.save(book);
        return "redirect:/books";

    }

    @GetMapping()
    public String showAllBooks(Model model) {
        model.addAttribute("books", bookDAO.showAllBooks());
        return "book/showAllBooks";
    }

    @GetMapping("/{id}")
    public String showBook(@PathVariable("id") int id, Model model,Person person) {
        model.addAttribute("book", bookDAO.showBook(id));
        model.addAttribute("person",person);
        Optional<Person> bookOwner=personDAO.showPersonByIdBook(id);
        if (bookOwner.isPresent()){
            model.addAttribute("owner",bookOwner);
        }
        else {
            model.addAttribute("people",personDAO.showAllPerson());
        }

        return "book/showBook";
    }

    @GetMapping("/{id}/editBook")
    public String editBook(@PathVariable("id") int id,
                           Model model) {
        model.addAttribute("book", bookDAO.showBook(id));
        return "book/editBook";
    }

    @PatchMapping("/{id}")
    public String editBook(@PathVariable("id") int id,
                           @ModelAttribute("book") Book book) {
        bookDAO.editBook(id, book);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/order")
    public String orderBook(@PathVariable("id") int id, @ModelAttribute("person") Person person) {
        bookDAO.orderBook(id, person);
        return "redirect:/books";
    }
    @PatchMapping("/{id}/return")
    public String returnBook(@PathVariable("id")int id){
        bookDAO.returnBook(id);
        return "redirect:/books";
    }


    @DeleteMapping("/{id}")
    public String deleteBook(@PathVariable("id") int id, @ModelAttribute("book") Book book) {
        bookDAO.deleteBook(id);
        return "redirect:/books";
    }


}
