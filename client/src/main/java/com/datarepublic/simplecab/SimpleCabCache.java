package com.datarepublic.simplecab;

import java.util.Map;
import java.util.Date;
import java.util.HashMap;

class SimpleCabCache {

    private Map<String, Integer> cache = new HashMap<>();

    public static SimpleCabCache load(String path) {
        return new SimpleCabCache();
    }

    private SimpleCabCache() {

    }

    public void save(String path) {

    }

    private String getKey(String medallion, Date pickupDate)
    {
        return medallion + SimpleCabUtil.DATE_FORMATTER.format(pickupDate);
    }

    public void put(String medallion, Date pickupDate, Integer trips)
    {
        cache.put(getKey(medallion, pickupDate), trips);
    }

    public Integer get(String medallion, Date pickupDate)
    {
        return cache.get(getKey(medallion, pickupDate));
    }

    public void clear()
    {
        cache.clear();
    }
}