package com.ifmo.imageserver.services;

import com.ifmo.imageserver.entity.Author;
import com.ifmo.imageserver.entity.Image;
import com.ifmo.imageserver.exceptions.ImageException;
import com.ifmo.imageserver.repository.AuthorRepository;
import com.ifmo.imageserver.repository.ImageRepository;
import com.ifmo.imageserver.specification.ImageSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Completes necessary tests with parameters from image controller and call repositories methods to get information
 */
@Service
public class ImageService {

    private ImageRepository repository;
    private AuthorRepository authorRepository;

    @Autowired
    public ImageService(ImageRepository repository, AuthorRepository authorRepository) {
        this.repository = repository;
        this.authorRepository = authorRepository;
    }

    /**
     * Add image into DB. Check author from image.
     * Search author from DB using authorID.
     * If author present into DB add this image to author's image list, if author is absent return authorID = -1
     * @param image for add to DB
     * @return added image
     * @throws ImageException if image already exist into DB
     */
    public Image add(Image image) {
        if (repository.findOne(ImageSpecification.findByFileName(image.getFileName())).isPresent())
            throw new ImageException("Image for add is already exist");
        if (authorRepository.existsById(image.getAuthorID())) {
            Author author = authorRepository.findById(image.getAuthorID()).get();
            image.setAuthor(author);
            author.addImage(image);
            authorRepository.save(author);
            return repository.save(image);
        }
        image.setAuthorID(-1L);
        return image;
    }

    /**
     * Update image in DB using filename of this image. Add image to author's image list
     * Delete old image from DB and from author's image list.
     * @param image which need to be update
     * @return updated image
     * @throws ImageException if image is not exist in DB
     */
    public Image update(Image image) {
        if (!repository.findOne(ImageSpecification.findByFileName(image.getFileName())).isPresent())
            throw new ImageException("Image for update is not exist");
        Image resultImage = repository.findOne(ImageSpecification.findByFileName(image.getFileName())).get();
        image.setAuthor(authorRepository.findById(image.getAuthorID()).get());
        Author author = authorRepository.findById(image.getAuthorID()).get();
        author.removeImage(resultImage);
        author.addImage(image);
        authorRepository.save(author);
        repository.delete(resultImage);
        return repository.save(image);
    }

    /**
     * Delete image from DB, remove this image from author's list of image.
     * @param image whcih will be deleted
     * @return deleted Image
     * @throws ImageException if image not found into DB
     */
    public Image delete(Image image) {
        if (!repository.findOne(ImageSpecification.findByFileName(image.getFileName())).isPresent())
            throw new ImageException("Image for delete is not exist");
        Image resultImage = repository.findOne(ImageSpecification.findByFileName(image.getFileName())).get();
        Author author = authorRepository.findById(resultImage.getAuthor().getId()).get();
        author.removeImage(resultImage);
        authorRepository.save(author);
        repository.delete(resultImage);
        return resultImage;
    }

    /**
     * Pageable Method for get all images from DB
     * @param page receiving number of page
     * @param size size of requested page
     * @return Image in Page class for client
     */
    public Page<Image> getByPage(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Image> imagePage = repository.findAll(pageable);
        if (imagePage.getContent().isEmpty()) throw new ImageException("Images not found");
        return imagePage;
    }

    /**
     * Get list of image which was made by author with this nickname
     * @param nickname of the author which made this image
     * @return list of image which was made by author with this nickname
     * @throws ImageException if nickname of author is less 3 characters
     */
    public Iterable<Image> getByAuthorNickname(String nickname) {
        if (Objects.isNull(nickname) || nickname.length() < 3)
            throw new ImageException("Nickname must be 3 or more characters (Image getByAuthorNickname request)");
        return repository.findAll(ImageSpecification.findByAuthorNickname(nickname));
    }

    /**
     * Get list of image which was made by author
     * @param author which made image
     * @return list of image which was made by author
     * @throws ImageException if author is null
     */
    public Iterable<Image> getByAuthor(Author author) {
        if (Objects.isNull(author)) throw new ImageException("Illegal author in getByAuthor request");
        String nickname = author.getNickname();
        return repository.findAll(ImageSpecification.findByAuthor(author));
    }

    /**
     * Get list of images which have this user's filename
     * @param fileName user's filename
     * @return list of images which have same user's filename
     */
    public Iterable<Image> getByFileName(String fileName) {
        if (Objects.isNull(fileName) || !fileName.contains("."))
            throw new ImageException("Illegal fileName in getByFileName request");
        Iterable<Image> result = repository.findAll(ImageSpecification.findByUserFileName(fileName));
        return result;
    }

    /**
     * Method get images which was made in this city
     * @param city where images was made
     * @return List of Images which was made in this city
     * @throws ImageException if city length less 3 characters
     */
    public Iterable<Image> getByCity(String city) {
        if (Objects.isNull(city) || city.length() < 3)
            throw new ImageException("Illegal city in getByCity request");
        return repository.findAll(ImageSpecification.findByCity(city));
    }

    /**
     * Method get images which was made in this country
     * @param country where images was made
     * @return List of Images which was made in this country
     * @throws ImageException if country's length less 3 characters
     */
    public Iterable<Image> getByCountry(String country) {
        if (Objects.isNull(country) || country.length() < 3)
            throw new ImageException("Illegal country in getByCity request");
        return repository.findAll(ImageSpecification.findByCountry(country));
    }

    /**
     * Method get images with this date
     * @param date where images was made
     * @return List of Images which was made in this time
     * @throws  ImageException if data is in the future
     */
    public Iterable<Image> getByDate(LocalDateTime date) {
        if (Objects.isNull(date) || date.isBefore(LocalDateTime.now()))
            throw new ImageException("Illegal date in getByDate request");
        return repository.findAll(ImageSpecification.findByDate(date));
    }

    /**
     * Get images made from date to date. If dateTo is null then returns images from date to now
     * @param dateFrom date from images was made
     * @param dateTo date to images was made
     * @return list of Images which was made from date to date
     * @throws ImageException if dateFrom in the future
     */
    public Iterable<Image> getFromDateToCurrent(LocalDateTime dateFrom, LocalDateTime dateTo) {
        if (Objects.isNull(dateFrom) || dateFrom.isBefore(LocalDateTime.now()))
            throw new ImageException("Illegal date in getFromDateToCurrent request");
        if (Objects.isNull(dateTo)) {
            dateTo = LocalDateTime.now();
            return repository.findAll(ImageSpecification.findFromDateToDate(dateFrom, dateTo));
        }
        return repository.findAll(ImageSpecification.findFromDateToDate(dateFrom, dateTo));
    }
}
