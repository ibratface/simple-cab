package com.datarepublic.simplecab;

import java.util.List;
import java.util.Date;
import java.io.IOException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.fluent.Request;

public class SimpleCabHttpService implements SimpleCabService {

    private String baseUrl;

    public SimpleCabHttpService(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    @Override
    public TripSummary getTripSummary(List<String> medallions, Date pickupDate) {
        TripSummary results = new TripSummary();
        String url = encodeUrl(medallions, pickupDate);
        System.out.println(url);

        try {
            String content = Request.Get(url)
                .connectTimeout(1000)
                .socketTimeout(1000)
                .execute()
                .returnContent()
                .asString();
            // System.out.println(content);
            results = SimpleCabUtil.JSON_MAPPER.readValue(content, TripSummary.class);
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