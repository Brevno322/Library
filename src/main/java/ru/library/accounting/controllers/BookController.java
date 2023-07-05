package ru.library.accounting.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.library.accounting.models.Book;
import ru.library.accounting.models.Person;
import ru.library.accounting.service.BookService;
import ru.library.accounting.service.PersonService;


import java.util.Optional;

@Controller
@RequestMapping("/books")
public class BookController {
    private final BookService bookService;

    private final PersonService personService;

    @Autowired
    public BookController(BookService bookService, PersonService personService) {
        this.bookService = bookService;
        this.personService = personService;
    }

    @GetMapping("/new")
    public String createBook(@ModelAttribute("book") Book book) {
        return "book/newBook";
    }

    @PostMapping("/new")
    public String newBook(@ModelAttribute("book") Book book) {
        bookService.saveBook(book);
        return "redirect:/books";

    }

    @GetMapping()
    public String showAllBooks(Model model) {
        model.addAttribute("books", bookService.showAllBooks());
        return "book/showAllBooks";
    }

    @GetMapping("/{id}")
    public String showBook(@PathVariable("id") int id, Model model, Person person) {
        model.addAttribute("book", bookService.showBook(id));
        model.addAttribute("person", person);
        Optional<Person> bookOwner = personService.showPersonByIdBook(id);
        if (bookOwner.isPresent()) {
            model.addAttribute("owner", bookOwner);
        } else {
            model.addAttribute("people", personService.index());
        }

        return "book/showBook";
    }

    @GetMapping("/{id}/editBook")
    public String editBook(@PathVariable("id") int id,
                           Model model) {
        model.addAttribute("book", bookService.showBook(id));
        return "book/editBook";
    }

    @PatchMapping("/{id}")
    public String editBook(@PathVariable("id") int id,
                           @ModelAttribute("book") Book book) {
        bookService.updateBook(id, book);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/order")
    public String orderBook(@PathVariable("id") int id, @ModelAttribute("person") Person person) {
        Book book=bookService.showBook(id);
        bookService.orderBook(id, person,book);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/return")
    public String returnBook(@PathVariable("id") int id) {
        bookService.returnBook(id);
        return "redirect:/books";
    }


    @DeleteMapping("/{id}")
    public String deleteBook(@PathVariable("id") int id, @ModelAttribute("book") Book book) {
        bookService.deleteBook(id);
        return "redirect:/books";
    }


}
