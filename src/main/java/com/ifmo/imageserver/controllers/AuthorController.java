package com.ifmo.imageserver.controllers;

import com.ifmo.imageserver.entity.Author;
import com.ifmo.imageserver.services.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/author")
public class AuthorController {

    private AuthorService service;

    @Autowired
    public AuthorController(AuthorService service) {
        this.service = service;
    }

    @PostMapping("/add")
    public Author addAuthorToDB(@RequestBody Author author) {
        return service.add(author);
    }

    @PutMapping("/update")
    public Author updateAuthorInDB(@RequestBody Author author) {
        return service.update(author);
    }

    @DeleteMapping("/delete")
    public Author deleteAuthorFromDB(@RequestBody Author author) {
        return service.delete(author);
    }

    @GetMapping
    public Page<Author> getAllFromDB(@RequestParam int page, @RequestParam int size) {
        Page<Author> imagePage;
        imagePage = service.getByPage(page, size);
        return imagePage;
    }

    @GetMapping("/nickname")
    public Author getAuthorByNicknameFromDB(@RequestParam String nickname) {
        return service.getByNickname(nickname);
    }

    @GetMapping("/city")
    public Iterable<Author> getAuthorByCityFromDB(@RequestParam String city) {
        return service.getByCity(city);
    }

    @GetMapping("/country")
    public Iterable<Author> getAuthorByCountryFromDB(@RequestParam String country) {
        return service.getByCountry(country);
    }
}
