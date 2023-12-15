package com.shopindia.payment.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JsonTransformer {
    private  final static ObjectMapper objectMapper = new ObjectMapper();

    public static String objectToString(Object object) throws IOException {
        String jsonString = objectMapper.writeValueAsString(object);
        return jsonString;
    }

    public static <T> T stringToObject(String jsonString, Class<T> className) throws IOException {
        T object = objectMapper.readValue(jsonString, className);

        return object;
    }

    public static <T> List<T> stringToObjectArray(String jsonString, Class<T> className) throws IOException {
        List<T> objectArray = objectMapper.reader()
                .forType(new TypeReference<List<T>>(){})
                .readValue(jsonString);

        return objectArray;
    }

    public static <T> List<T> stringToObjectListOfGivenType(String jsonString, Class<T> className) throws JsonProcessingException {
        List<T> objList = null;

        objList = objectMapper.readValue(
                jsonString,
                TypeFactory.defaultInstance().constructParametrizedType(
                        ArrayList.class,
                        List.class,
                        className)
        );

        return objList;
    }
}
