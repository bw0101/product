package com.bee.product.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class JsonService {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public <T> String marshal(T objectToMarshal) throws JsonProcessingException {
        return objectMapper.writeValueAsString(objectToMarshal);
    }

    public <T> T unmarshal(String json, Class<T> targetClass) throws IOException {
        return objectMapper.readValue(json, targetClass);
    }
}
