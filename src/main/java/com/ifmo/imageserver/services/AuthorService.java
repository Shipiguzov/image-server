package com.ifmo.imageserver.services;

import com.ifmo.imageserver.entity.Author;
import com.ifmo.imageserver.exceptions.AuthorException;
import com.ifmo.imageserver.repository.AuthorRepository;
import com.ifmo.imageserver.specification.AuthorSpecification;
import org.apache.tomcat.util.http.parser.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class AuthorService {

    private AuthorRepository repository;

    @Autowired
    public AuthorService(AuthorRepository repository) {
        this.repository = repository;
    }

    private void setRepository(AuthorRepository repository) {
        if (Objects.isNull(repository)) throw new AuthorException("Author repository is null");
        this.repository = repository;
    }

    public Author add(Author author) {
        if (Objects.isNull(author) ||
                repository.findOne(AuthorSpecification.findByNickname(author.getNickname())).isPresent())
            throw new AuthorException("Author with this nickname already exist");
        return repository.save(author);
    }

    public Author update(Author author) {
        if (Objects.isNull(author) ||
                !repository.findOne(AuthorSpecification.findByNickname(author.getNickname())).isPresent())
            throw new AuthorException("Author not found in update request");
        return repository.save(author);
    }

    public Author delete(Author author) {
        if (Objects.isNull(author) ||
                !repository.findOne(AuthorSpecification.findByNickname(author.getNickname())).isPresent())
            throw new AuthorException("Author not found in update request");
        Author returnAuthor = repository.findOne(AuthorSpecification.findByNickname(author.getNickname())).get();
        repository.delete(returnAuthor);
        return returnAuthor;
    }

    public Page<Author> getByPage(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Author> authorPage = repository.findAll(pageable);
        if (authorPage.isEmpty()) throw new AuthorException("There is no author in DB");
        return authorPage;
    }

    public Author getByNickname(String nickname) {
        if (Objects.isNull(nickname) || nickname.length() < 3)
            throw new AuthorException("Author's nickname is  in getByNickname request");
        return repository.findOne(AuthorSpecification.findByNickname(nickname)).get();
    }

    public Iterable<Author> getByCity(String city) {
        if (Objects.isNull(city) || city.length() < 3)
            throw new AuthorException("Author's city is null in getByCity request");
        return repository.findAll(AuthorSpecification.findByCity(city));
    }

    public Iterable<Author> getByCountry(String country) {
        if (Objects.isNull(country) || country.length() < 3)
            throw new AuthorException("Author's country is null in getByCountry request");
        return repository.findAll(AuthorSpecification.findByCountry(country));
    }

    public Author getByID(long id) {
        return repository.findById(id).get();
    }

    // TODO: Finish methods with age
//    public Iterable<Author> getByAge(int age) {
//        if (age < 3 || age > 120)
//            throw new AuthorException("Author's age is out of range in getByAge request");
//        return repository.findAll(AuthorSpecification.findByAge(age));
//    }
//
//    public Iterable<Author> getByFromAgeToAge(Author authorFrom, Author authorTo) {
//        if (authorFrom.getAge() < 3 || authorFrom.getAge() > 120 || authorTo.getAge() < 3 || authorTo.getAge() > 120)
//            throw new AuthorException("Author's age is out of range in getByFromAgeToAge request");
//        return repository.findByFromAgeToAge(authorFrom.getAge(), authorTo.getAge());
//    }
}
