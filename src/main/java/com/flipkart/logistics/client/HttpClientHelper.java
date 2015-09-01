package com.flipkart.logistics.client;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;

/**
 * Created by vishal.bhandari on 31/08/15.
 */
public class HttpClientHelper {

    public HttpResponse postRequest(String url,String jsonString) throws IOException

    {
        HttpResponse response;
        HttpClient httpClient = HttpClientBuilder.create().build();
        StringEntity postString = new StringEntity(jsonString);
        HttpPost request = new HttpPost(url);
        request.setHeader("Content-Type", "application/json");
        request.setHeader("Accept", "*/*");


        request.setEntity(postString);
        response = httpClient.execute(request);
        return response;
    }

}
