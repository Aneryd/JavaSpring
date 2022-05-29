package com.example.test_spring.controller;

import com.example.test_spring.models.Film;
import com.example.test_spring.repo.FilmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Optional;

@Controller
public class MainController {

    @Autowired
    private FilmRepository filmRepository;

    @GetMapping("/")
    public String index(Model model){
        Iterable<Film> films = filmRepository.findAll();
        model.addAttribute("title", "Главная страница");
        model.addAttribute("films", films);
        return "index";
    }

    @GetMapping("/add")
    public String add(Model model){
        return "add";
    }

    @PostMapping("/add")
    public String addFilm(@RequestParam String title, @RequestParam String description,
                          @RequestParam String year,   @RequestParam String country,  @RequestParam String date, Model model){
        Film film = new Film(title, description, year, country, date);
        filmRepository.save(film);
        return "redirect:/";
    }

    @GetMapping("/film/{id}/edit")
    public String filmEdit(@PathVariable(value="id") long id, Model model){
        if(!filmRepository.existsById((id))){
            return "redirect:/";
        }

        Optional<Film> film = filmRepository.findById(id);
        ArrayList<Film> res = new ArrayList<>();
        film.ifPresent(res::add);
        model.addAttribute("film", res);
        return "edit";
    }

    @PostMapping("/film/{id}/edit")
    public String editFilm(@PathVariable(value="id") long id, @RequestParam String title, @RequestParam String description,
                          @RequestParam String year,   @RequestParam String country,  @RequestParam String date, Model model){
        Film film = filmRepository.findById(id).orElseThrow();
        film.setTitle(title);
        film.setDescription(description);
        film.setYear(year);
        film.setCountry(country);
        film.setDate(date);
        filmRepository.save(film);
        return "redirect:/";
    }

    @PostMapping("/delete/{id}")
    public String deleteFilm(@PathVariable(value="id") long id,  Model model){
        Film film = filmRepository.findById(id).orElseThrow();
        filmRepository.delete(film);
        return "redirect:/";
    }
}
