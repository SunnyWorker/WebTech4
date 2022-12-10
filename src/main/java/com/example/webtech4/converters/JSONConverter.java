package com.example.webtech4.converters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JSONConverter {
    private ObjectMapper objectMapper;

    public JSONConverter() {
        this.objectMapper = new ObjectMapper();
    }

    public String convertToJSON(Object o) {
        try {
            String json = objectMapper.writeValueAsString(o);
            return json;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public<T> T getObjectFromJSON(String json) {
        T t = null;
        try {
            t = (T) objectMapper.readValue(json,t.getClass());
            return t;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
