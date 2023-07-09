package ru.library.accounting.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.library.accounting.models.Book;
import ru.library.accounting.models.Person;
import ru.library.accounting.service.BookService;
import ru.library.accounting.service.PersonService;

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
    public String showAllBooks(Model model, @RequestParam(value = "page", required = false) Integer page,
                               @RequestParam(value = "books_per_page", required = false) Integer booksParePage,
                               @RequestParam(value = "sort_by_year", required = false) boolean sortByYear) {
        if (page == null || booksParePage == null) {
            model.addAttribute("books", bookService.showAllBooks(sortByYear));
        } else
            model.addAttribute("books", bookService.findWithPagination(page, booksParePage, sortByYear));
        return "book/showAllBooks";
    }

    @GetMapping("/{id}")
    public String showBook(@PathVariable("id") int id, Model model, @ModelAttribute("person") Person person) {
        model.addAttribute("book", bookService.showBook(id));

        Person bookOwner=bookService.getBookOwner(id);
        if (bookOwner != null) {
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
        bookService.orderBook(id, person);
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

    @GetMapping("/search")
    public String search() {
        return "book/search";
    }

    @PostMapping("/search")
    public String makeSearch(@RequestParam("name") String name, Model model) {
        model.addAttribute("books", bookService.searchBook(name));
        return "book/search";
    }


}
