package com.datarepublic.simplecab;

import java.util.List;
import java.util.Map;
import java.util.Date;

public interface SimpleCabService {

    Map<String, Integer> getTripCountsBy(List<String> medallions, Date pickupDate);

}
