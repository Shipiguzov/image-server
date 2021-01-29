package com.ifmo.imageserver.entity;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.ifmo.imageserver.exceptions.ImageException;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Entity class to define Image class in DB
 */
@Entity
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id", scope = Long.class)
@NoArgsConstructor
@Table(name = "image")
public class Image extends BaseIdentify {

    /**
     * Field of Author who made this Image
     */
    @JsonBackReference
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @Getter
    @ManyToOne(fetch = FetchType.LAZY)
    private Author author;

    @Getter
    @Setter
    @Transient
    private long authorID;

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
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime date;

    /**
     * Field of file name of this Image. How it named on server.
     * This name will be like id_of_image_in_DB.*
     */
    @Getter
    @Column(length = 100)
    private String fileName;

    /**
     * Field of user file name. Use to take extension of file
     */
    @Getter
    @Column(length = 100)
    private String userFileName;

    /**
     * Field of image file in byte array
     */
    @Getter
    @Setter
    @Transient
    private byte[] byteArray;

    public Image(String userFileName, Author author) {
        setUserFileName(userFileName);
        setAuthor(author);
        setFileName();
        authorID = author.getId();
    }

    /**
     * Method set a Author of this Image. Check author is null
     *
     * @param author
     */
    public void setAuthor(Author author) {
        if (Objects.isNull(author))
            throw new ImageException("Author is null");
        this.author = author;
    }

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

    /**
     * Method transform user file name to id_of_image_in_DB.user_extension
     */
    public void setFileName() {
        String[] array = userFileName.split("\\.");
        StringBuilder fileNameBuilder = new StringBuilder();
        fileNameBuilder = fileNameBuilder.append(getId()).append(".").append(array[array.length - 1]);
        fileName = fileNameBuilder.toString();
    }

    /**
     * Method set user file name
     *
     * @param userFileName user file name
     */
    public void setUserFileName(String userFileName) {
        if (Objects.isNull(userFileName) || userFileName.length() < 5 || !userFileName.contains("."))
            throw new ImageException("File name must be 5 or more characters");
        this.userFileName = userFileName;
    }
}
