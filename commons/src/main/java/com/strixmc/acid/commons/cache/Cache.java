package com.strixmc.acid.commons.cache;

import java.util.Map;
import java.util.Optional;

/**
 * This class is used to provide the methods to manage the cached objects.
 *
 * @param <K> key from stored object in cache.
 * @param <V> object stored in cache.
 */

public interface Cache<K, V> {

    /**
     * @return all cached objects stored in this map.
     */
    Map<K, V> get();

    /**
     * Gets an optional object from cache using unique ID.
     *
     * @param unique Saved object unique ID.
     * @return Optional object from cache map.
     */
    default Optional<V> find(K unique) {
        return Optional.ofNullable(get().get(unique));
    }

    /**
     * Remove saved object from cache using unique ID.
     *
     * @param unique Saved object unique ID.
     */
    default void remove(K unique) {
        get().remove(unique);
    }

    /**
     * Save object to cache using a unique ID.
     *
     * @param unique Object's unique ID.
     * @param object Object to save.
     */
    default void add(K unique, V object) {
        get().put(unique, object);
    }

    /**
     * @param unique Saved object unique ID.
     * @return if the object is saved in cache with specified unique ID.
     */
    default boolean exists(K unique) {
        return get().containsKey(unique);
    }

    /**
     * @param unique Saved object unique ID.
     * @return Saved object if cache contains object with the unique ID, else will return null.
     */
    default V getIfPresent(K unique) {
        if (exists(unique)) return get().get(unique);
        return null;
    }

}
