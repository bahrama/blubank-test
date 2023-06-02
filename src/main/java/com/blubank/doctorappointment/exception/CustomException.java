package com.blubank.doctorappointment.exception;

import com.blubank.doctorappointment.dto.response.ApiErrorResponse;

import java.util.Map;

public class CustomException extends RuntimeException {
    public ApiErrorResponse error;
    public CustomException(String message) {
        super(message);
    }

    public CustomException(ApiErrorResponse error){
        this.error = error;
    }

}
