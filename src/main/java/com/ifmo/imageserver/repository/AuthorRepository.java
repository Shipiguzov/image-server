package com.ifmo.imageserver.repository;

import com.ifmo.imageserver.entity.Author;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Interface for work with Author repository. Take necessary information from DB using author specifications.
 */
public interface AuthorRepository extends PagingAndSortingRepository<Author, Long>, JpaSpecificationExecutor<Author> {

//    @Query("SELECT c FROM author c WHERE UPPER(c.nickname) = UPPER(:nickname)")
//    Author findByNickname(@Param("nickname") String nickname);
//
//    @Query("SELECT c FROM author c WHERE UPPER(c.city) = UPPER(:city)")
//    Iterable<Author> findByCity(@Param("city") String city);
//
//    @Query("SELECT c FROM author c WHERE UPPER(c.country) = UPPER(:country)")
//    Iterable<Author> findByCountry(@Param("country") String country);
//
//    @Query("SELECT c FROM author c WHERE c.age = :age")
//    Iterable<Author> findByAge(@Param("age") int age);
//
//    @Query("SELECT c FROM author c WHERE c.age >= ageFrom AND c.age <= ageTo")
//    Iterable<Author> findByFromAgeToAge(@Param("ageFrom") int age, @Param("ageTo") int ageTo);
}
