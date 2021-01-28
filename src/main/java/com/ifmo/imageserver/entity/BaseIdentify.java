package com.ifmo.imageserver.entity;

import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class BaseIdentify {

    /**
     * unique ID of elements in DB
     */
    @Id
    // TODO: remove @Setter after debugging
    @Setter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    /**
     * Method for receive id of element from DB
     * @return id of DB element
     */
    public long getId() {
        return id;
    }
}
