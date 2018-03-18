package com.datarepublic.simplecab.repository;

import java.util.Calendar;
import java.util.Date;

import javax.annotation.ManagedBean;
import java.text.SimpleDateFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.cache.annotation.Cacheable;

@ManagedBean
public class SimpleCabJdbcRepository implements SimpleCabRepository {
    private static final String SQL_DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final SimpleDateFormat SQL_DATETIME_FORMATTER = new SimpleDateFormat(SQL_DATETIME_FORMAT);

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    @Cacheable("trips")
    public Integer getTripCountBy(String medallion, Date pickupDate) {
        Calendar startDate = clearTime(pickupDate);
        Calendar endDate = clearTime(pickupDate);
        endDate.add(Calendar.DAY_OF_MONTH, 1);

        return jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM cab_trip_data " + "WHERE medallion = ? " + "AND pickup_datetime >= ? "
                        + "AND pickup_datetime < ?",
                new Object[] { medallion, SQL_DATETIME_FORMATTER.format(startDate.getTime()),
                        SQL_DATETIME_FORMATTER.format(endDate.getTime()) },
                Integer.class);
    }

    private Calendar clearTime(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal;
    }
}