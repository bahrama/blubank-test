package com.blubank.doctorappointment.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@Setter
public class ApiErrorResponse {
    private int statusCode;
    private HttpStatus status;
    private String message;
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "dd-mm-yyyy hh:mm:ss")
    private LocalDateTime timeStamp;
    public ApiErrorResponse(){}
    public ApiErrorResponse(int statusCode, HttpStatus status, String message, LocalDateTime timeStamp) {
        this.statusCode = statusCode;
        this.status = status;
        this.message = message;
        this.timeStamp = timeStamp;
    }
}
