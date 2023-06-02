package com.blubank.doctorappointment.controller;

import com.blubank.doctorappointment.dto.UserDto;
import com.blubank.doctorappointment.dto.response.ApiSuccessResponse;
import com.blubank.doctorappointment.dto.response.JWTLoginSucessReponse;
import com.blubank.doctorappointment.service.MapValidationErrorService;
import com.blubank.doctorappointment.service.UserService;
import com.blubank.doctorappointment.service.UserValidator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final MapValidationErrorService mapValidationErrorService;

    private final UserService userService;

    private final UserValidator userValidator;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserDto userDto, BindingResult result) {
        userValidator.validate(userDto,result);
        ResponseEntity<?> errorMap = mapValidationErrorService.mapValidationService(result);
        if(errorMap!=null)
            return errorMap;
        userService.save(userDto);
        return new ResponseEntity<ApiSuccessResponse>(new ApiSuccessResponse(200, HttpStatus.CREATED), HttpStatus.CREATED);
    }

}
