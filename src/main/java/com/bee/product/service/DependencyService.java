package com.bee.product.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.net.URI;
import java.net.URISyntaxException;
import java.io.IOException;

@Service
//@AllArgsConstructor
public class DependencyService {

   // private RestTemplate restTemplate;

   /* @Autowired
    public DependencyService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }*/

    public String fetchUrlContent(String urlString) throws URISyntaxException, IOException {
        URI uri = new URI(urlString);

        //return restTemplate.getForObject(uri, String.class);
        return  uri.toString();
    }
}
