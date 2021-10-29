package com.strixmc.acid.commons.cache;

import java.util.Set;

public class GenericBaseCache<T> implements GenericCache<T> {

    private final Set<T> cacheContainer;

    public GenericBaseCache(Set<T> cacheContainer) {
        this.cacheContainer = cacheContainer;
    }

    @Override
    public Set<T> get() {
        return this.cacheContainer;
    }
}
