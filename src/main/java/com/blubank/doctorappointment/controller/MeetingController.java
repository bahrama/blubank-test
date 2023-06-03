package com.blubank.doctorappointment.controller;

import com.blubank.doctorappointment.dto.MeetingDto;
import com.blubank.doctorappointment.dto.MeetingDtoManager;
import com.blubank.doctorappointment.dto.request.ReserveRequest;
import com.blubank.doctorappointment.dto.response.ApiSuccessResponse;
import com.blubank.doctorappointment.service.MapValidationErrorService;
import com.blubank.doctorappointment.service.MeetingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/meeting")
public class MeetingController {

    private final MeetingService meetingService;

    private final MapValidationErrorService mapValidationErrorService;

    @PutMapping
    public ResponseEntity<?> addMeeting(@Valid @RequestBody MeetingDto meetingDto
            , BindingResult result){
        ResponseEntity<?> errorMap = mapValidationErrorService.mapValidationService(result);
        if(errorMap!=null) return errorMap;
        meetingService.saveChunk(meetingDto);
        return new ResponseEntity<>(new ApiSuccessResponse(200,HttpStatus.OK),HttpStatus.OK);
    }

    @PutMapping("/reserve")
    public ResponseEntity<?> reserveMeeting(@Valid @RequestBody ReserveRequest reserveRequest,BindingResult result){
        ResponseEntity<?> errorMap = mapValidationErrorService.mapValidationService(result);
        if(errorMap!=null) return errorMap;
        meetingService.reserveMeeting(reserveRequest);
        return new ResponseEntity<>(new ApiSuccessResponse(200,HttpStatus.OK),HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> findMeeting(@Valid @RequestBody MeetingDto search,BindingResult result){
        ResponseEntity<?> errorMap = mapValidationErrorService.mapValidationService(result);
        if(errorMap!=null) return errorMap;
        return new ResponseEntity<>(meetingService.findMeetingList(search),HttpStatus.OK);
    }

    @DeleteMapping("{meetingId}")
    public ResponseEntity<?> reserveMeeting(@PathVariable Long meetingId){
        meetingService.removeMeeting(meetingId);
        return new ResponseEntity<>(new ApiSuccessResponse(200,HttpStatus.OK),HttpStatus.OK);
    }
}
