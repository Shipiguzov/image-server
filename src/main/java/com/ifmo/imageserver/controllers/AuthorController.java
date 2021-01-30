package com.ifmo.imageserver.controllers;

import com.ifmo.imageserver.entity.Author;
import com.ifmo.imageserver.services.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

/**
 * Class for processing of HTTP request for author class.
 * Takes requests from Tomcat server and depends on this call necessary method from author service
 */
@RestController
@RequestMapping(value = "/author")
public class AuthorController {

    private AuthorService service;

    @Autowired
    public AuthorController(AuthorService service) {
        this.service = service;
    }

    /**
     * Prosessing request for add author into DB
     * @param author author for add to DB
     * @return Author which was added to DB
     */
    @PostMapping("/add")
    public Author addAuthorToDB(@RequestBody Author author) {
        System.out.println(author.getNickname());
        return service.add(author);
    }

    /**
     * Processing request for update author in DB
     * @param author author for modification in DB
     * @return Author which was modified
     */
    @PutMapping("/update")
    public Author updateAuthorInDB(@RequestBody Author author) {
        return service.update(author);
    }

    /**
     * Deleting author from DB
     * @param author author which will be deleted from DB
     * @return Author which was deleted from DB
     */
    @DeleteMapping("/delete")
    public Author deleteAuthorFromDB(@RequestBody Author author) {
        return service.delete(author);
    }

    /**
     * Pageable Method for get all authors from DB
     * @param page receiving number of page
     * @param size size of requested page
     * @return Authors in Page class for client
     */
    @GetMapping
    public Page<Author> getAllFromDB(@RequestParam int page, @RequestParam int size) {
        Page<Author> imagePage;
        imagePage = service.getByPage(page, size);
        return imagePage;
    }

    /**
     * Method for get author by nickname
     * @param nickname nickname of needed author
     * @return Author with this nickname
     */
    @GetMapping("/nickname")
    public Author getAuthorByNicknameFromDB(@RequestParam String nickname) {
        System.out.println(nickname);
        return service.getByNickname(nickname);
    }

    /**
     * Search author who living in this city
     * @param city when author living
     * @return list of Author from this city
     */
    @GetMapping("/city")
    public Iterable<Author> getAuthorByCityFromDB(@RequestParam String city) {
        System.out.println(city);
        return service.getByCity(city);
    }

    /**
     * Search author who living in this country
     * @param country when author living
     * @return list of Author from this country
     */
    @GetMapping("/country")
    public Iterable<Author> getAuthorByCountryFromDB(@RequestParam String country) {
        System.out.println(country);
        return service.getByCountry(country);
    }
}
