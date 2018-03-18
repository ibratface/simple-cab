package com.datarepublic.simplecab;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.Date;

class SimpleCabCache {

    private TripSummary cache = null;

    public SimpleCabCache(String path) {
        try {
            this.cache = SimpleCabUtil.JSON_MAPPER.readValue(new FileReader(path), TripSummary.class);
        } catch (Exception e) {
            this.cache = new TripSummary();
        }
    }

    public void save(String path) {
        try {
            SimpleCabUtil.JSON_MAPPER.writeValue(new FileWriter(path), cache);
        } catch (Exception e) {
            System.err.println("Failed to save cache.");
        }
    }

    private String getKey(String medallion, Date pickupDate) {
        return medallion + "_" + SimpleCabUtil.DATE_FORMATTER.format(pickupDate);
    }

    public boolean contains(String medallion, Date pickupDate) {
        return cache.containsKey(getKey(medallion, pickupDate));
    }

    public void put(String medallion, Date pickupDate, Integer trips) {
        cache.put(getKey(medallion, pickupDate), trips);
    }

    public Integer get(String medallion, Date pickupDate) {
        return cache.get(getKey(medallion, pickupDate));
    }

    public void clear() {
        cache.clear();
    }
}