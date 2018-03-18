package com.datarepublic.simplecab.controller;
import com.datarepublic.simplecab.repository.SimpleCabRepository;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMethod;

@RestController
public final class SimpleCabController {
    
    private static final String DATE_PARAM_FORMAT = "yyyy-MM-dd";

    @Autowired
    SimpleCabRepository repository;

    @RequestMapping(value="/report/trips", method=RequestMethod.GET)
    public
    @ResponseBody Map<String, Integer>
    reportTrips(
        @RequestParam(value="medallion") List<String> medallions,
        @RequestParam(value="date") @DateTimeFormat(pattern=DATE_PARAM_FORMAT) Date pickupDate) 
    {
        Map<String, Integer> trips = new LinkedHashMap<>();
        medallions.forEach(medallion -> { 
            trips.put(medallion, repository.getTripCountBy(medallion, pickupDate));
        });
        
        return trips;
    }
}