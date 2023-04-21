package com.bsuir.lr.demo.cache;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class Cache<K, V> {
    private final Map<String, Double> cacheMap = new HashMap<>();

    public void put(String key, Double value) {
        cacheMap.put(key, value);
    }

    public Double get(String key) {
        return cacheMap.get(key);
    }

    public void delete(String key) {
        cacheMap.remove(key);
    }

    public void clear() {
        cacheMap.clear();
    }
}
