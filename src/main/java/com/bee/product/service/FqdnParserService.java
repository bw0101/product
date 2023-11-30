package com.bee.product.service;


import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Paths;
import java.net.URISyntaxException;

@Service
@AllArgsConstructor
public class FqdnParserService {

    private DependencyService dependencyService;

   /* @Autowired
    public FqdnParserService(DependencyService dependencyService){
        this.dependencyService = dependencyService;
    }*/

    public String fetchUrlContent(String urlString) throws URISyntaxException, IOException {
        return dependencyService.fetchUrlContent(urlString);
    }

    public enum Prefix {
        PREFIX_ONE,
        PREFIX_TWO,
        PREFIX_THREE
        // Add as many prefix values as you need.
    }

    public String parseFqdn(String urlString) throws URISyntaxException {
        URI uri = new URI(urlString);
        return uri.getHost();
    }

    public String getFilename(String urlString) throws URISyntaxException {
        URI uri = new URI(urlString);
        String path = uri.getPath();
        String fileName = Paths.get(path).getFileName().toString();
        return fileName;
    }

    public String getFileExtension(String urlString) throws URISyntaxException {
        String filename = getFilename(urlString);
        int dotIndex = filename.lastIndexOf(".");
        return (dotIndex == -1) ? "" : filename.substring(dotIndex + 1);
    }

    public String getFilePrefix(String urlString) throws URISyntaxException {
        String filename = getFilename(urlString);
        for (Prefix prefix : Prefix.values()) {
            if (filename.startsWith(prefix.name())) {
                return prefix.name();
            }
        }
        return "";
    }

}