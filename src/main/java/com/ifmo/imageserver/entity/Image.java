package com.ifmo.imageserver.entity;

import com.ifmo.imageserver.exceptions.ImageException;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

/**
 * Entity class to define Image class in DB
 */
@Entity
@NoArgsConstructor
@Table(name = "image")
public class Image extends BaseIdentify {

    /**
     * Field of Author who made this Image
     */
    @Getter
    @ManyToOne(fetch = FetchType.LAZY)
    private Author author;

    /**
     * Field of additional image info (like date, country etc)
     */
    @Getter
    @ManyToOne(fetch = FetchType.LAZY)
    private AdditionalImageInfo info;

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

    public Image(String userFileName, Author author) {
        setUserFileName(userFileName);
        setAuthor(author);
        setFileName();
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
     * Method set AdditionalImageInfo for Image
     * @param info additional image info (like city, country etc.)
     */
    public void setAdditionalImageInfo(AdditionalImageInfo info) {
        if (Objects.isNull(info)) throw new ImageException("Additional image info is null");
        this.info = info;
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
        if (Objects.isNull(userFileName) || userFileName.length() < 5 || !userFileName.contains("\\."))
            throw new ImageException("File name must be 5 or more characters");
        this.userFileName = userFileName;
    }
}
