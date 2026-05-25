package com.example;

public class InvalidFieldValueException extends IllegalArgumentException {
    public InvalidFieldValueException(String message) {
        super(message);
    }
}