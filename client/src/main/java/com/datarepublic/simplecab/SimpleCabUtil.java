package com.datarepublic.simplecab;

import java.text.SimpleDateFormat;

import com.fasterxml.jackson.databind.ObjectMapper;

public class SimpleCabUtil {
    public static final String DATE_PARAM_FORMAT = "yyyy-MM-dd";
    public static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat(DATE_PARAM_FORMAT);
    public static final ObjectMapper JSON_MAPPER = new ObjectMapper();
}