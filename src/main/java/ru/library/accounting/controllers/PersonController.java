package ru.library.accounting.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.library.accounting.models.Person;
import ru.library.accounting.service.BookService;
import ru.library.accounting.service.PersonService;


@Controller
@RequestMapping("/people")
public class PersonController {
    private final PersonService personService;




    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;

    }

    @GetMapping()
    public String showAllPerson(Model model) {
        model.addAttribute("people", personService.index());
        return "person/showAllPerson";
    }

    @GetMapping("/{id}")
    public String showPersonId(@PathVariable("id") int id, Model model) {
        model.addAttribute("person", personService.showPersonById(id));
        model.addAttribute("books", personService.getBooksByPersonId(id));
        return "person/showPersonId";
    }

    @GetMapping("/new")
    public String createPerson(@ModelAttribute("person") Person person) {
        return "person/newPerson";
    }

    @PostMapping
    public String create(@ModelAttribute("persona") Person person) {
        personService.savePerson(person);
        return "redirect:/people";
    }

    @GetMapping("/{id}/edit")
    public String editPerson(@PathVariable("id") int id, Model model) {
        model.addAttribute("person", personService.showPersonById(id));
        return "person/edit";
    }

    @PatchMapping("/{id}")
    public String updatePerson(@PathVariable("id") int id, @ModelAttribute("person") Person person) {
        personService.updatePerson(id, person);
        return "redirect:/people";
    }

    @DeleteMapping("/{id}")
    public String deletePerson(@PathVariable("id") int id, @ModelAttribute("person") Person person) {
        personService.deletePerson(id);
        return "redirect:/people";

    }

}
