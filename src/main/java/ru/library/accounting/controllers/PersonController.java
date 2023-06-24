package ru.library.accounting.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.library.accounting.dao.BookDAO;
import ru.library.accounting.dao.PersonDAO;
import ru.library.accounting.model.Person;
import ru.library.accounting.util.BookValidation;
import ru.library.accounting.util.PersonValidation;

@Controller
@RequestMapping("/people")
public class PersonController {
    private final PersonDAO personDAO;
    private final PersonValidation personValidation;
    private final BookDAO bookDAO;
    private final BookValidation bookValidation;

    @Autowired
    public PersonController(PersonDAO personDAO, PersonValidation personValidation, BookDAO bookDAO, BookValidation bookValidation) {
        this.personDAO = personDAO;
        this.personValidation = personValidation;
        this.bookDAO = bookDAO;
        this.bookValidation = bookValidation;
    }

    @GetMapping()
    public String showAllPerson(Model model) {
        model.addAttribute("people", personDAO.showAllPerson());
        return "person/showAllPerson";
    }

    @GetMapping("/{id}")
    public String showPersonId(@PathVariable("id") int id, Model model) {
        model.addAttribute("person", personDAO.showPersonId(id));
        model.addAttribute("books",bookDAO.showBookByIdPerson(id));
        return "person/showPersonId";
    }

    @GetMapping("/new")
    public String createPerson(@ModelAttribute("person") Person person) {
        return "person/newPerson";
    }

    @PostMapping
    public String create(@ModelAttribute("persona") Person person) {
        personDAO.save(person);
        return "redirect:/people";
    }
    @GetMapping("/{id}/edit")
    public String editPerson(@PathVariable("id") int id,Model model){
        model.addAttribute("person",personDAO.showPersonId(id));
        return"person/edit";
    }

    @PatchMapping("/{id}")
    public String updatePerson(@PathVariable("id") int id,@ModelAttribute("person") Person person){
       personDAO.updatePerson(id,person);
        return "redirect:/people";
    }

    @DeleteMapping("/{id}")
    public String deletePerson(@PathVariable("id") int id,@ModelAttribute("person") Person person){
        personDAO.delete(id);
        return "redirect:/people";

    }

}
