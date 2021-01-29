package com.ifmo.imageserver.specification;

import com.ifmo.imageserver.entity.*;
import com.ifmo.imageserver.entity.Author_;
import com.ifmo.imageserver.entity.Image_;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.time.LocalDateTime;

public class ImageSpecification {

    public static Specification<Image> findByFileName(String fileName) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(Image_.fileName), fileName);
    }

    public static Specification<Image> findByUserFileName(String userFileName) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(Image_.userFileName), userFileName);
    }

    public static Specification<Image> findByAuthor(Author author) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get(Image_.author), author);
    }

    public static Specification<Image> findByAuthorNickname(String nickname) {
        return (root, query, criteriaBuilder) -> {
            Join<Image, Author> imageAuthor = root.join(Image_.author, JoinType.LEFT);
            return criteriaBuilder.equal(imageAuthor.get(Author_.nickname), nickname);
        };
    }

    public static Specification<Image> findByCity(String city) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(Image_.city), city);
    }

    public static Specification<Image> findByCountry(String country) {
        return (root, query, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get(Image_.country), country);
        };
    }

    public static Specification<Image> findByDate(LocalDateTime date) {
        return (root, query, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get(Image_.date), date);
        };
    }

    public static Specification<Image> findFromDateToDate(LocalDateTime fromDate, LocalDateTime toDate) {
        return (root, query, criteriaBuilder) -> {
            Predicate fromDt = criteriaBuilder.greaterThanOrEqualTo(root.get(Image_.date), fromDate);
            Predicate toDt = criteriaBuilder.lessThanOrEqualTo(root.get(Image_.date), toDate);
            return criteriaBuilder.and(toDt, fromDt);
        };
    }
}
