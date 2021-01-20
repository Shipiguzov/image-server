package com.ifmo.imageserver.specification;

import com.ifmo.imageserver.entity.*;
import com.ifmo.imageserver.entity.AdditionalImageInfo_;
import com.ifmo.imageserver.entity.Author_;
import com.ifmo.imageserver.entity.Image_;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import java.time.LocalDateTime;

public class ImageSpecification {

    public static Specification<Image> findByUserFileName(String userFileName) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(Image_.USER_FILE_NAME), userFileName);
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
        return (root, query, criteriaBuilder) -> {
            Join<Image, AdditionalImageInfo> imageAdditionalImageInfo = root.join(Image_.info, JoinType.LEFT);
            return criteriaBuilder.equal(imageAdditionalImageInfo.get(AdditionalImageInfo_.city), city);
        };
    }

    public static Specification<Image> findByCountry(String country) {
        return (root, query, criteriaBuilder) -> {
            Join<Image, AdditionalImageInfo> imageAdditionalImageInfo = root.join(Image_.info, JoinType.LEFT);
            return criteriaBuilder.equal(imageAdditionalImageInfo.get(AdditionalImageInfo_.country), country);
        };
    }

    public static Specification<Image> findByDate(LocalDateTime date) {
        return (root, query, criteriaBuilder) -> {
            Join<Image, AdditionalImageInfo> imageAdditionalImageInfo = root.join(Image_.info, JoinType.LEFT);
            return criteriaBuilder.equal(imageAdditionalImageInfo.get(AdditionalImageInfo_.date), date);
        };
    }

    public static Specification<Image> findFromDateToDate(LocalDateTime fromDate, LocalDateTime toDate) {
        return (root, query, criteriaBuilder) -> {
            Join<Image, AdditionalImageInfo> imageAdditionalImageInfo = root.join(Image_.info, JoinType.LEFT);
            Predicate fromDt = criteriaBuilder.greaterThanOrEqualTo(imageAdditionalImageInfo.get(AdditionalImageInfo_.DATE), fromDate);
            Predicate toDt = criteriaBuilder.lessThanOrEqualTo(imageAdditionalImageInfo.get(AdditionalImageInfo_.date), toDate);
            CriteriaBuilder builder;
            return criteriaBuilder.and(toDt, fromDt);
        };
    }
}
