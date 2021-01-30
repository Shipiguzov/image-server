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

/**
 * Class for processing of HTTP request for image class.
 * Takes requests from Tomcat server and depends on this call necessary method from image service
 */
@RestController
@RequestMapping(value = "/image")
public class ImageController {

    private ImageService service;

    @Autowired
    public ImageController(ImageService service, AuthorService authorService) {
        this.service = service;
    }

    /**
     * Method for add image into DB. If Author is not present into DB returns authorID = -1.
     * @param image which will be added to DB
     * @return added into DB Image
     */
    @PostMapping("/add")
    public Image addImageToDB(@RequestBody Image image) {
        service.add(image);
        FileWork.saveImageToDisk(image);
        return image;
    }

    /**
     * Method for update image in DB
     * @param image which will be updated in DB
     * @return updated in DB Image
     */
    @PutMapping("/update")
    public Image updateImageInDB(@RequestBody Image image) {
        Image resultImage = service.update(image);
        FileWork.saveImageToDisk(resultImage);
        return resultImage;
    }

    /**
     * Method for delete image from DB
     * @param image deleted image from DB
     * @return deleted Image from DB
     */
    @DeleteMapping("/delete")
    public Image deleteImageFromDB(@RequestBody Image image) {
        Image resultImage = service.delete(image);
        FileWork.deleteImageFromDisk(resultImage);
        return resultImage;
    }

    /**
     * Pageable Method for get all images from DB
     * @param page receiving number of page
     * @param size size of requested page
     * @return Image in Page class for client
     */
    @GetMapping
    public Page<Image> getAllFromDB(@RequestParam int page, @RequestParam int size) {
        Page<Image> imagePage;
        imagePage = service.getByPage(page, size);
        return imagePage;
    }

    /**
     * Method for get images made by author with this nickname
     * @param nickname nickname of author
     * @return List of Images which was made by Author with this nickname
     */
    @GetMapping("/nickname")
    public Iterable<Image> getByAuthorNicknameFromDB(@RequestParam String nickname) {
        return service.getByAuthorNickname(nickname);
    }

    /**
     * Method get images made by this author
     * @param author which made images
     * @return List of Images which was made by this author
     */
    @GetMapping("/author")
    public Iterable<Image> getByAuthorFromDB(@RequestBody Author author) {
        return service.getByAuthor(author);
    }

    /**
     * Method get images of this user's filename
     * @param fileName user's filename. Not the same that filename of image on server
     * @return List of Images with this user's filename
     */
    @GetMapping("/byFileName")
    public Iterable<Image> getByFileNameFromDB(@RequestParam String fileName) {
        return service.getByFileName(fileName);
    }

    /**
     * Method get images which was made in this city
     * @param city where images was made
     * @return List of Images which was made in this city
     */
    @GetMapping("/city")
    public Iterable<Image> getByCityFromDB(@RequestParam String city){
        return service.getByCity(city);
    }

    /**
     * Method get images which was made in this country
     * @param country where images was made
     * @return List of Images which was made in this country
     */
    @GetMapping("/country")
    public Iterable<Image> getByCountryFromDB(@RequestParam String country) {
        return service.getByCountry(country);
    }

    /**
     * Method get images with this date
     * @param date where images was made
     * @return List of Images which was made in this time
     */
    @GetMapping("/date")
    public Iterable<Image> getByDateFromDB(@RequestParam LocalDateTime date) {
        return service.getByDate(date);
    }
}
