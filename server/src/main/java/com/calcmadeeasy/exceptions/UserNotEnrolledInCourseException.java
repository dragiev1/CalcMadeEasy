package com.calcmadeeasy.exceptions;

public class UserNotEnrolledInCourseException extends RuntimeException {
    public UserNotEnrolledInCourseException(String message) {
        super(message);
    }
}
