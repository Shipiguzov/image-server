package com.ifmo.imageserver.specification;

import com.ifmo.imageserver.entity.AdditionalImageInfo_;
import com.ifmo.imageserver.entity.Author;
import com.ifmo.imageserver.entity.Author_;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import java.time.LocalDate;

public class AuthorSpecification {

    public static Specification<Author> findByNickname(String nickname) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(Author_.nickname), nickname);
    }

    public static Specification<Author> findByCity(String city) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(Author_.city), city);
    }

    public static Specification<Author> findByCountry(String country) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(Author_.country), country);
    }

    // TODO: Finish methods with age
//    public static Specification<Author> findByAge(int age) {
//        return (root, query, criteriaBuilder) ->
//                criteriaBuilder.equal(root.get(Author_.birthDate), LocalDate.now().minusYears(age));
//    }
//
//    public static Specification<Author> findFromAgeToAge(int fromAge, int toAge) {
//        return (root, query, criteriaBuilder) -> {
//            Predicate fromDt = criteriaBuilder.greaterThanOrEqualTo(root.get(Author_.birthDate), fromAge);
//            Predicate toDt = criteriaBuilder.lessThanOrEqualTo(imageAdditionalImageInfo.get(AdditionalImageInfo_.date), toDate);
//            CriteriaBuilder builder;
//            return criteriaBuilder.and(toDt, fromDt);
//        };
//    }
}
