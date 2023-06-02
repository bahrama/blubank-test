package com.blubank.doctorappointment.dto.response;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ApiSuccessResponse {
    private int statusCode;
    private HttpStatus status;

    public ApiSuccessResponse(int statusCode, HttpStatus status) {
        this.statusCode = statusCode;
        this.status = status;
    }
}
