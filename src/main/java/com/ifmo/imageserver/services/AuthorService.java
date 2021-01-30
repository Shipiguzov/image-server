package com.ifmo.imageserver.services;

import com.ifmo.imageserver.entity.Author;
import com.ifmo.imageserver.exceptions.AuthorException;
import com.ifmo.imageserver.repository.AuthorRepository;
import com.ifmo.imageserver.specification.AuthorSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * Completes necessary tests with parameters from author controller and call repository's method to get information`
 */
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

    /**
     * Add author in DB.
     * @param author author for add
     * @return added author
     * @throws AuthorException if this author not exist into DB already.
     */
    public Author add(Author author) {
        if (Objects.isNull(author) ||
                repository.findOne(AuthorSpecification.findByNickname(author.getNickname())).isPresent())
            throw new AuthorException("Author with this nickname already exist");
        return repository.save(author);
    }

    /**
     * Update author in DB.
     * @param author for update into DB
     * @return updated author
     * @throws AuthorException if author from request not null and check that author from request present into DB
     */
    public Author update(Author author) {
        if (Objects.isNull(author) ||
                !repository.findOne(AuthorSpecification.findByNickname(author.getNickname())).isPresent())
            throw new AuthorException("Author not found in update request");
        return repository.save(author);
    }

    /**
     * Delete author from DB.
     * @param author for delete from DB
     * @return deleted author
     * @throws AuthorException if author from request not null and check that author from request present into DB
     */
    public Author delete(Author author) {
        if (Objects.isNull(author) ||
                !repository.findOne(AuthorSpecification.findByNickname(author.getNickname())).isPresent())
            throw new AuthorException("Author not found in update request");
        Author returnAuthor = repository.findOne(AuthorSpecification.findByNickname(author.getNickname())).get();
        repository.delete(returnAuthor);
        return returnAuthor;
    }

    /**
     * Get all author by Page from DB
     * @param page number of necessary page
     * @param size of necessary page
     * @return Needed page with Authors with this size
     * @throws AuthorException if page or size less 1
     */
    public Page<Author> getByPage(int page, int size) {
        if (page < 1 || size < 1) throw new AuthorException("Page of size less 1 into getByPage request");
        Pageable pageable = PageRequest.of(page, size);
        Page<Author> authorPage = repository.findAll(pageable);
        if (authorPage.isEmpty()) throw new AuthorException("There is no author in DB");
        return authorPage;
    }

    /**
     * Get author with nickname.
     * @param nickname of searching author
     * @return Author with this nickname
     * @throws AuthorException if nickname is not null and length of nickname more or equal 3.
     */
    public Author getByNickname(String nickname) {
        if (Objects.isNull(nickname) || nickname.length() < 3)
            throw new AuthorException("Author's nickname length less 3 in getByNickname request");
        return repository.findOne(AuthorSpecification.findByNickname(nickname)).get();
    }

    /**
     * Get authors from city
     * @param city where authors lives
     * @return list of author from this city.
     * @throws AuthorException if Author's city length is less 3
     */
    public Iterable<Author> getByCity(String city) {
        if (Objects.isNull(city) || city.length() < 3)
            throw new AuthorException("Author's city length is less 3 in getByCity request");
        return repository.findAll(AuthorSpecification.findByCity(city));
    }

    /**
     * Get authors from country
     * @param country where authors lives
     * @return list of author from this country.
     * @throws AuthorException if Author's country length is less 3
     */
    public Iterable<Author> getByCountry(String country) {
        if (Objects.isNull(country) || country.length() < 3)
            throw new AuthorException("Author's country is null in getByCountry request");
        return repository.findAll(AuthorSpecification.findByCountry(country));
    }

    /**
     * Get author from ID
     * @param id of author
     * @return Author with this ID or null if not found.
     */
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
