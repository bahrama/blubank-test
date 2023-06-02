package com.blubank.doctorappointment.controller;

import com.blubank.doctorappointment.dto.MeetingDto;
import com.blubank.doctorappointment.dto.MeetingDtoManager;
import com.blubank.doctorappointment.dto.request.ReserveRequest;
import com.blubank.doctorappointment.dto.response.ApiSuccessResponse;
import com.blubank.doctorappointment.service.MeetingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/meeting")
public class MeetingController {

    private final MeetingService meetingService;

    @PutMapping
    public ResponseEntity<?> addMeeting(@RequestBody MeetingDto meetingDto
            , BindingResult result){
        meetingService.saveChunk(meetingDto);
        return new ResponseEntity<>(new ApiSuccessResponse(200,HttpStatus.OK),HttpStatus.OK);
    }

    @PutMapping("/reserve")
    public ResponseEntity<?> reserveMeeting(@RequestBody ReserveRequest reserveRequest,BindingResult result){
        meetingService.reserveMeeting(reserveRequest);
        return new ResponseEntity<>(new ApiSuccessResponse(200,HttpStatus.OK),HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> findMeeting(@RequestBody MeetingDto search,BindingResult result){
        return new ResponseEntity<>(meetingService.findMeetingList(search),HttpStatus.OK);
    }

    @DeleteMapping("{meetingId}")
    public ResponseEntity<?> reserveMeeting(@PathVariable Long meetingId){
        meetingService.removeMeeting(meetingId);
        return new ResponseEntity<>(new ApiSuccessResponse(200,HttpStatus.OK),HttpStatus.OK);
    }
}
