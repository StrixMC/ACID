package com.strixmc.acid.commons.cache;

import java.util.Set;

public interface GenericCache<T> {

    /**
     * @return all cached objects stored in this set.
     */
    Set<T> get();

    /**
     * Save object into cache.
     *
     * @param object Object to save.
     */
    default void add(T object) {
        get().add(object);
    }

    /**
     * Remove saved object from cache.
     *
     * @param object Saved object unique ID.
     */
    default void remove(T object) {
        get().remove(object);
    }

    /**
     * @param object Saved object.
     * @return if the object is saved in cache.
     */
    default boolean exists(T object) {
        return get().contains(object);
    }

    /**
     * @return if cache is empty.
     */
    default boolean isEmpty() {
        return get().isEmpty();
    }

}
