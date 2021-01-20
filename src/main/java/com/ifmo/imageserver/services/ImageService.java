package com.ifmo.imageserver.services;

import com.ifmo.imageserver.entity.Author;
import com.ifmo.imageserver.entity.Image;
import com.ifmo.imageserver.exceptions.ImageException;
import com.ifmo.imageserver.repository.ImageRepository;
import com.ifmo.imageserver.specification.ImageSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

@Service
public class ImageService {

    private ImageRepository repository;

    @Autowired
    public ImageService(ImageRepository repository) {
        setRepository(repository);
    }

    private void setRepository(ImageRepository repository) {
        if (Objects.isNull(repository)) throw new ImageException("Image repository is null");
        this.repository = repository;
    }

    public Image add(Image image) {
        if (repository.existsById(image.getId()))
            throw new ImageException("Image for add is already exist");
        return repository.save(image);
    }

    public Image update(Image image) {
        if (!repository.existsById(image.getId()))
            throw new ImageException("Image for update is not exist");
        return repository.save(image);
    }

    public Image delete(Image image) {
        if (!repository.existsById(image.getId()))
            throw new ImageException("Image for delete is not exist");
        repository.delete(image);
        return image;
    }

    public Page<Image> getByPage(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Image> imagePage = repository.findAll(pageable);
        if (imagePage.getContent().isEmpty()) throw new ImageException("Images not found");
        return imagePage;
    }

    public Iterable<Image> getByAuthorNickname(String nickname) {
        if (Objects.isNull(nickname) || nickname.length() < 3)
            throw new ImageException("Nickname must be 3 or more characters (Image getByAuthorNickname request)");
        return repository.findAll(ImageSpecification.findByAuthorNickname(nickname));
    }

    public Iterable<Image> getByAuthor(Author author) {
        if (Objects.isNull(author)) throw new ImageException("Illegal author in getByAuthor request");
        String nickname = author.getNickname();
        return repository.findAll(ImageSpecification.findByAuthor(author));
    }

    public Image getByFileName(String fileName) {
        if (Objects.isNull(fileName) || !fileName.contains("\\."))
            throw new ImageException("Illegal fileName in getByFileName request");
        Optional<Image> result = repository.findOne(ImageSpecification.findByUserFileName(fileName));
        return result.get();
    }

    public Iterable<Image> getByCity(String city) {
        if (Objects.isNull(city) || city.length() < 3)
            throw new ImageException("Illegal city in getByCity request");
        return repository.findAll(ImageSpecification.findByCity(city));
    }

    public Iterable<Image> getByCountry(String country) {
        if (Objects.isNull(country) || country.length() < 3)
            throw new ImageException("Illegal country in getByCity request");
        return repository.findAll(ImageSpecification.findByCountry(country));
    }

    public Iterable<Image> getByDate(LocalDateTime date) {
        if (Objects.isNull(date) || date.isBefore(LocalDateTime.now()))
            throw new ImageException("Illegal date in getByDate request");
        return repository.findAll(ImageSpecification.findByDate(date));
    }

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
