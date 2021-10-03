package com.strixmc.acid.commons.storage;

import java.util.Optional;

public interface Storage<T> {


    /**
     * @param unique object identifier.
     * @return {@link T} object from storage using unique ID as identifier.
     */
    Optional<T> find(String unique);

    /**
     * Load everything from storage.
     */
    void loadAll();

    /**
     * Save all objects to storage.
     * Override method if it will be used.
     */
    default void saveAll() {

    }

    /**
     * Save object into storage.
     *
     * @param object To save object.
     */
    void save(T object);

    /**
     * Delete object from storage using unique ID.
     *
     * @param unique Stored object unique ID.
     */
    void delete(String unique);

    /**
     * @param unique Stored object unique ID.
     * @return if object with specified unique id exits.
     */
    boolean exists(String unique);

}
