package com.ifmo.imageserver.controllers;

import com.ifmo.imageserver.FileWork;
import com.ifmo.imageserver.entity.Author;
import com.ifmo.imageserver.entity.Image;
import com.ifmo.imageserver.services.AuthorService;
import com.ifmo.imageserver.services.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping(value = "/image")
public class ImageController {

    private ImageService service;

    @Autowired
    public ImageController(ImageService service, AuthorService authorService) {
        this.service = service;
    }

    @PostMapping("/add")
    public Image addImageToDB(@RequestBody Image image) {
        service.add(image);
        FileWork.saveImageToDisk(image);
        return image;
    }

    @PutMapping("/update")
    public Image updateImageInDB(@RequestBody Image image) {
        Image resultImage = service.update(image);
        FileWork.saveImageToDisk(resultImage);
        return resultImage;
    }

    @DeleteMapping("/delete")
    public Image deleteImageFromDB(@RequestBody Image image) {
        Image resultImage = service.delete(image);
        FileWork.deleteImageFromDisk(resultImage);
        return resultImage;
    }

    @GetMapping
    public Page<Image> getAllFromDB(@RequestParam int page, @RequestParam int size) {
        Page<Image> imagePage;
        imagePage = service.getByPage(page, size);
        return imagePage;
    }

    @GetMapping("/nickname")
    public Iterable<Image> getByAuthorNicknameFromDB(@RequestParam String nickname) {
        return service.getByAuthorNickname(nickname);
    }

    @GetMapping("/author")
    public Iterable<Image> getByAuthorFromDB(@RequestBody Author author) {
        return service.getByAuthor(author);
    }

    @GetMapping("/byFileName")
    public Iterable<Image> getByFileNameFromDB(@RequestParam String fileName) {
        return service.getByFileName(fileName);
    }

    @GetMapping("/city")
    public Iterable<Image> getByCityFromDB(@RequestParam String city){
        return service.getByCity(city);
    }

    @GetMapping("/country")
    public Iterable<Image> getByCountryFromDB(@RequestParam String country) {
        return service.getByCountry(country);
    }

    @GetMapping("/date")
    public Iterable<Image> getByDateFromDB(@RequestParam LocalDateTime date) {
        return service.getByDate(date);
    }
}
