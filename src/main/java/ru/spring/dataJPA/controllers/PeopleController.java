package ru.spring.dataJPA.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.spring.dataJPA.models.Person;
import ru.spring.dataJPA.services.PeopleServices;

import javax.validation.Valid;

@Component
@RequestMapping("/people")
public class PeopleController {
    private final PeopleServices peopleServices;

    @Autowired
    public PeopleController(PeopleServices peopleServices) {
        this.peopleServices = peopleServices;
    }

    @GetMapping
    public String allPeople(Model model) {
        System.out.println("привет");
        model.addAttribute("people", peopleServices.findAllPeople());
        return "people/allPeople";
    }

    @GetMapping("{id}")
    public String person(@PathVariable("id") int id, Model model) {
        model.addAttribute("person", peopleServices.findOne(id));
        return "people/person";
    }

    @GetMapping("/new")
    public String newPerson(@ModelAttribute("person") Person person) {
        return "people/newPerson";
    }

    @PostMapping
    public String createPerson(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) return "people/newPerson";
        System.out.println("привет");
        peopleServices.save(person);
        return "redirect:/people";
    }

    @GetMapping("{id}/edit")
    public String edit(@PathVariable("id") int id, Model model) {
        model.addAttribute("person", peopleServices.findOne(id));
        return "people/edit";
    }

    @PatchMapping("{id}")
    public String update(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult, @PathVariable("id") int id) {
        if (bindingResult.hasErrors()) return "people/edit";
        peopleServices.update(person, id);
        return "people/person";
    }

    @DeleteMapping("{id}")
    public String delete(@PathVariable("id") int id) {
        peopleServices.delete(id);
        return "redirect:/people";
    }
}
