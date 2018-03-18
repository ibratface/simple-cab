package com.datarepublic.simplecab.repository;

import java.util.Date;

public interface SimpleCabRepository {

  Integer getTripCountBy(String medallion, Date pickupDate); 

}
