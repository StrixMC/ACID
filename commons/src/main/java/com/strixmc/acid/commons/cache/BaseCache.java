package com.strixmc.acid.commons.cache;

import javax.inject.Singleton;
import java.util.Map;

/**
 * @param <K> Object unique ID
 * @param <V> Object to save into cache map.
 */
@Singleton
public class BaseCache<K, V> implements Cache<K, V> {

    private final Map<K, V> cacheMap;

    public BaseCache(Map<K, V> cacheMap) {
        this.cacheMap = cacheMap;
    }

    /**
     * @return Cache map.
     */
    @Override
    public Map<K, V> get() {
        return this.cacheMap;
    }


}
