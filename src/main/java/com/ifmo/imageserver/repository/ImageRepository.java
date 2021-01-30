package com.ifmo.imageserver.repository;

import com.ifmo.imageserver.entity.Image;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Interface for work with Image repository. Take necessary information from DB using image specifications.
 */
public interface ImageRepository extends PagingAndSortingRepository<Image, Long>, JpaSpecificationExecutor<Image> {

//    @Query("SELECT c FROM image c WHERE UPPER(c.userFilename) = UPPER(:userFileName)")
//    Iterable<Image> findByFileName(@Param("userFileName") String userFileName);
//
//    @Query("SELECT c FROM image c WHERE UPPER(c.city) = UPPER(:city)")
//    Iterable<Image> findByCity(@Param("city") String city);
//
//    @Query("SELECT c FROM image c WHERE UPPER(c.country) = UPPER(:country)")
//    Iterable<Image> findByCountry(@Param("country") String country);
//
//    @Query("SELECT c FROM image c WHERE c.date = :date")
//    Iterable<Image> findByDate(@Param("date") LocalDateTime date);
//
//    @Query("SELECT c FROM image c WHERE c.date >= DATE(:dateFrom) AND c.date <= dateTo")
//    Iterable<Image> findFromDateToDate(@Param("dateFrom") LocalDateTime dateFrom, @Param("dateTo") LocalDateTime dateTo);
//
//    @Query("SELECT c FROM image c WHERE c.author = (SELECT g FROM author g WHERE UPPER(g.nickname) = UPPER(:nickname))")
//    Iterable<Image> findByAuthor(@Param("author") String nickname);
}
