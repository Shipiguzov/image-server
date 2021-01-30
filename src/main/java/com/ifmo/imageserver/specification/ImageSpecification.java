package com.ifmo.imageserver.specification;

import com.ifmo.imageserver.entity.*;
import com.ifmo.imageserver.entity.Author_;
import com.ifmo.imageserver.entity.Image_;
import com.ifmo.imageserver.exceptions.ImageException;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.time.LocalDateTime;

public class ImageSpecification {

    /**
     * Method get Specification which can use to find image with this filename
     * @param fileName filename of image that stores on server
     * @return Specification which can use to find image with this filename
     */
    public static Specification<Image> findByFileName(String fileName) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(Image_.fileName), fileName);
    }

    /**
     * Method get Specification which can use to find image with this filename
     * @param userFileName user's filename. Not the same that filename of image on server
     * @return Specification which can use to find images with this user's filename
     */
    public static Specification<Image> findByUserFileName(String userFileName) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(Image_.userFileName), userFileName);
    }

    /**
     * Method get Specification which can use to find images made by this author
     * @param author which made images
     * @return Specification which can use to find images made by this author
     */
    public static Specification<Image> findByAuthor(Author author) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get(Image_.author), author);
    }

    /**
     * Method for get Specification which can use to find images made by author with this nickname
     * @param nickname nickname of author
     * @return Specification which can use to find images made by author with this nickname
     */
    public static Specification<Image> findByAuthorNickname(String nickname) {
        return (root, query, criteriaBuilder) -> {
            Join<Image, Author> imageAuthor = root.join(Image_.author, JoinType.LEFT);
            return criteriaBuilder.equal(imageAuthor.get(Author_.nickname), nickname);
        };
    }

    /**
     * Method get Specification which can use to find images was made in this city
     * @param city where images was made
     * @return Specification which can use to find images was made in this city
     */
    public static Specification<Image> findByCity(String city) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(Image_.city), city);
    }

    /**
     * Method get Specification which can use to find images was made in this country
     * @param country where images was made
     * @return Specification which can use to find images was made in this country
     */
    public static Specification<Image> findByCountry(String country) {
        return (root, query, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get(Image_.country), country);
        };
    }

    /**
     * Method get Specification which can use to find image was made in this date
     * @param date where images was made
     * @return Specification which can use to find image was made in this date
     */
    public static Specification<Image> findByDate(LocalDateTime date) {
        return (root, query, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get(Image_.date), date);
        };
    }

    /**
     * Get Specification which can use to find images was made from date to date
     * @param fromDate date from images was made
     * @param toDate date to images was made
     * @return Specification which can use to find images was made from date to date
     */
    public static Specification<Image> findFromDateToDate(LocalDateTime fromDate, LocalDateTime toDate) {
        return (root, query, criteriaBuilder) -> {
            Predicate fromDt = criteriaBuilder.greaterThanOrEqualTo(root.get(Image_.date), fromDate);
            Predicate toDt = criteriaBuilder.lessThanOrEqualTo(root.get(Image_.date), toDate);
            return criteriaBuilder.and(toDt, fromDt);
        };
    }
}
