package com.ifmo.imageserver.entity;

import com.ifmo.imageserver.exceptions.ImageException;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@NoArgsConstructor
@Table(name = "additionalimageinfo")
public class AdditionalImageInfo extends BaseIdentify {

    /**
     * Field of city where image was made
     */
    @Getter
    @Column(length = 100)
    private String city;

    /**
     * Field of country where image was made
     */
    @Getter
    @Column(length = 100)
    private String country;

    /**
     * Field of date where image was made
     */
    @Getter
    private LocalDateTime date;

    /**
     * List of images with this info
     */
    @Getter
    @OneToMany(mappedBy = "info", fetch = FetchType.LAZY)
    private List<Image> images;

    /**
     * Method to set city where image was made
     * @param city city where image was made
     */
    public void setCity(String city) {
        if (Objects.isNull(city) || city.length() < 3)
            throw new ImageException("City must be 3 or more characters");
        this.city = city;
    }

    /**
     * Method to set country where image was made
     * @param country country where image was made
     */
    public void setCountry(String country) {
        if (Objects.isNull(country) || country.length() < 3)
            throw new ImageException("Country must be 3 or more characters");
        this.country = country;
    }

    /**
     * Method to set date when image was made
     * @param date date when image was made
     */
    public void setDate(LocalDateTime date) {
        if (date.isAfter(LocalDateTime.now()))
            throw new ImageException("Date is in the future");
        this.date = date;
    }
}
