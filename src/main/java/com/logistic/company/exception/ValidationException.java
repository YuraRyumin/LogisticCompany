package com.logistic.company.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ValidationException extends Exception {
    private final String message;
}
