package ru.otus.spring.jdbc.exception;

public class DataAccessException extends Exception {
    public DataAccessException(String message) {
        super(message);
    }
}
