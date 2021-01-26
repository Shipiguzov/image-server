package com.ifmo.imageserver.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.ifmo.imageserver.exceptions.AuthorException;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Entity class to define Author class in DB
 */
@Entity
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@NoArgsConstructor
@Table(name = "author")
public class Author extends BaseIdentify {

    /**
     * Field name of Author
     */
    @Getter
    @Column(length = 50)
    private String name;

    /**
     * Field of Author nickname
     */
    @Getter
    @Column(length = 50, nullable = false)
    private String nickname;

    /**
     * Field surname of Author
     */
    @Getter
    @Column(length = 50)
    private String surname;

    /**
     * Field birthdate of Author
     */
    @Getter
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate birthDate;

    /**
     * Field country were Author lives
     */
    @Getter
    @Column(length = 50)
    private String country;

    /**
     * Field city were Author lives
     */
    @Getter
    @Column(length = 50)
    private String city;

    /**
     * Field images of this Author
     */
    //@JsonBackReference
    @JsonIgnore
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @Getter
    @Setter
    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY)
    private List<Image> images = new ArrayList<>();

    public Author(String nickname) {
        setNickname(nickname);
    }

    /**
     * Method for set Author name. Must be 3 or more characters
     *
     * @param name name of images Author
     */
    public void setName(String name) {
        if (Objects.isNull(name) || name.length() < 3)
            throw new AuthorException("Name of author must be 3 or more characters");
        this.name = name;
    }

    public void setNickname(String nickname) {
        if (Objects.isNull(nickname) || nickname.length() < 3)
            throw new AuthorException("Nickname must be 3 or more characters");
        this.nickname = nickname;
    }

    /**
     * Method for set Author surname. Must be 3 or more characters
     *
     * @param surname surname of images Author
     */
    public void setSurname(String surname) {
        if (Objects.isNull(surname) || surname.length() < 3)
            throw new AuthorException("Surname of author must be 3 or more characters");
        this.surname = surname;
    }

    /**
     * Method for set Author birthdate. Age must be from 3 to 120
     *
     * @param date birthdate of Author
     */
    public void setBirthdate(LocalDate date) {
        if (LocalDate.now().minusYears(3).isBefore(date) || LocalDate.now().minusYears(120).isAfter(date))
            throw new AuthorException("Age of author must be from 3 to 120");
        this.birthDate = date;
    }

    /**
     * Method for set Author country. Must be 3 or more characters
     *
     * @param country country where Author lives
     */
    public void setCountry(String country) {
        if (Objects.isNull(country) || country.length() < 3)
            throw new AuthorException("Country where author live must be 3 or more characters");
        this.country = country;
    }

    /**
     * Method for set Author city. Must be 3 or more characters
     *
     * @param city city were Author lives
     */
    public void setCity(String city) {
        if (Objects.isNull(city) || city.length() < 3)
            throw new AuthorException("City where author live must be 3 or more characters");
        this.city = city;
    }

    /**
     * Method add Image to images of Author. Check is Image is null
     *
     * @param image image which Author made
     */
    public void addImage(Image image) {
        if (Objects.isNull(image)) throw new AuthorException("Image is null");
        this.images.add(image);
    }
}
