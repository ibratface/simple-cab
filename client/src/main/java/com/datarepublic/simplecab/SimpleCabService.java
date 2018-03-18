package com.datarepublic.simplecab;

import java.util.List;
import java.util.Date;

public interface SimpleCabService {

    TripSummary getTripSummary(List<String> medallions, Date pickupDate);

}
