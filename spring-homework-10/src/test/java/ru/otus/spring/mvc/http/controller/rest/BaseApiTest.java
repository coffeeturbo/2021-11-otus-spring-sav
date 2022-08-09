package ru.otus.spring.mvc.http.controller.rest;


import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class BaseApiTest {
    protected static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
