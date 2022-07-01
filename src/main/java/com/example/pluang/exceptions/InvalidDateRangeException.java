package com.example.pluang.exceptions;

import lombok.Builder;

@Builder
public class InvalidDateRangeException {
    private String message;
}
