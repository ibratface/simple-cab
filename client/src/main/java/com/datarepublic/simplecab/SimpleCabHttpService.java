package com.datarepublic.simplecab;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.fluent.Request;

import java.io.IOException;
import java.util.Date;

public class SimpleCabHttpService implements SimpleCabService {

    private String baseUrl;

    public SimpleCabHttpService(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    @Override
    public Map<String, Integer> getTripCountsBy(List<String> medallions, Date pickupDate) {
        Map<String, Integer> results = null;
        String url = encodeUrl(medallions, pickupDate);

        try {
            System.out.println(url);
            String content = Request.Get(url)
                .connectTimeout(1000)
                .socketTimeout(1000)
                .execute()
                .returnContent()
                .asString();
            System.out.println(content);
            ObjectMapper mapper = new ObjectMapper();
            results = mapper.readValue(content, Map.class);
            results.forEach((k, v) -> {
                System.out.println(k);
                System.out.println(v);
            });
        } catch (ClientProtocolException e) {
            // System.err.println(e);
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("Could not connect to the server.");
            // e.printStackTrace();
        }

        return results;
    }

    private String encodeUrl(List<String> medallions, Date pickupDate) {
        StringBuffer url = new StringBuffer();
        url.append(this.baseUrl);
        url.append("/report/trips?");
        medallions.forEach(m -> {
            url.append("medallion=");
            url.append(m);
            url.append("&");
        });
        url.append("date=");
        url.append(SimpleCabUtil.DATE_FORMATTER.format(pickupDate));
        return url.toString();
    }

}