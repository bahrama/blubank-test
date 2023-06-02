package com.blubank.doctorappointment.exception;

import com.blubank.doctorappointment.dto.response.ApiErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;

@Slf4j
@RestController
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    ApiErrorResponse error = new ApiErrorResponse();

    @ExceptionHandler
    public ResponseEntity<Object> handleEntityNotFoundException(EntityNotFoundException exc , WebRequest webRequest){

        error.setStatus(HttpStatus.NOT_FOUND);
        error.setMessage(exc.getMessage());
        error.setTimeStamp(LocalDateTime.now());
        error.setStatusCode(HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(error, error.getStatus());
    }
    @ExceptionHandler
    public ResponseEntity<Object> handleCustomException(CustomException exc , WebRequest webRequest) {
        error.setStatus(exc.error.getStatus());
        error.setMessage(exc.error.getMessage());
        error.setTimeStamp(exc.error.getTimeStamp());
        error.setStatusCode(exc.error.getStatusCode());
        return new ResponseEntity<>(error, error.getStatus());
    }
    @ExceptionHandler
    public ResponseEntity<Object> handleUniqException(DataIntegrityViolationException exc , WebRequest webRequest) {
        error.setStatus(HttpStatus.BAD_REQUEST);
        error.setMessage("unique constraint happened");
        error.setTimeStamp(LocalDateTime.now());
        error.setStatusCode(556);
        return new ResponseEntity<>(error, error.getStatus());
    }


}
