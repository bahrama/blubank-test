package com.blubank.doctorappointment.service;

import com.blubank.doctorappointment.dao.meeting.MeetingRepository;
import com.blubank.doctorappointment.dao.user.UserRepository;
import com.blubank.doctorappointment.dto.MeetingDto;
import com.blubank.doctorappointment.dto.MeetingDtoManager;
import com.blubank.doctorappointment.dto.request.ReserveRequest;
import com.blubank.doctorappointment.dto.response.ApiErrorResponse;
import com.blubank.doctorappointment.dto.response.MeetingReponse;
import com.blubank.doctorappointment.exception.CustomException;
import com.blubank.doctorappointment.model.Meeting;
import com.blubank.doctorappointment.model.User;
import com.blubank.doctorappointment.utils.Roles;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Tuple;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MeetingService {
    private final MeetingRepository meetingRepository;

    private final UserRepository userRepository;

    private MeetingDtoManager meetingDtoManager = Mappers.getMapper(MeetingDtoManager.class);

    public void saveChunk(MeetingDto meetingDto){
        Long endPrimaryEndTime = meetingDto.getEndMeetingDate().getTime();
        Long startTime = meetingDto.getMeetingDate().getTime();
        if(endPrimaryEndTime<startTime)
        throw new CustomException(new ApiErrorResponse(400, HttpStatus.BAD_REQUEST,"invalid input time", LocalDateTime.now()));
        if(endPrimaryEndTime-startTime<1800000)
        throw new CustomException(new ApiErrorResponse(400, HttpStatus.BAD_REQUEST,"invalid period time", LocalDateTime.now()));
        while (startTime < endPrimaryEndTime)
        {
            meetingDto.setMeetingDate(new Date(startTime));
            meetingDto.setEndMeetingDate(new Date(startTime+1800000));
            save(meetingDto);
            startTime = startTime + 1800000;
        }
    }

    @Transactional
    public void save(MeetingDto meetingDto){
        Meeting meeting = meetingDtoManager.transferDtoToMeetingEntity(meetingDto);
        Optional<User> user = userRepository.findUserByMobile(meetingDto.getMobile());
        if(!user.isPresent())
        throw new CustomException(new ApiErrorResponse(400, HttpStatus.BAD_REQUEST,"doctor not found", LocalDateTime.now()));
        meeting.setDoctor(user.get());
        meetingRepository.save(meeting);
    }



    @Transactional
    public void reserveMeeting(ReserveRequest reserveRequest){
        Optional<Meeting> meeting = meetingRepository.findById(reserveRequest.getMeetingId());
        Optional<User> user = userRepository.findUserByMobile(reserveRequest.getPatientMobile());
        if(!meeting.isPresent())
        throw new CustomException(new ApiErrorResponse(400, HttpStatus.BAD_REQUEST,"meeting not found", LocalDateTime.now()));
        else if(!user.isPresent())
        throw new CustomException(new ApiErrorResponse(400, HttpStatus.BAD_REQUEST,"user not found", LocalDateTime.now()));
        else if (meeting.get().getPatient()!=null) {
        throw new CustomException(new ApiErrorResponse(400, HttpStatus.BAD_REQUEST,"meeting already reserved", LocalDateTime.now()));
        } else if (meeting.get().getIsDeleted()) {
            throw new CustomException(new ApiErrorResponse(400, HttpStatus.BAD_REQUEST,"meeting is deleted", LocalDateTime.now()));
        } else {
            meeting.get().setPatient(user.get());
            meetingRepository.updateMeeting(meeting.get());
        }
    }

    @Transactional
    public void removeMeeting(Long meetingId){
        Optional<Meeting> meeting = meetingRepository.findById(meetingId);
        if(!meeting.isPresent())
            throw new CustomException(new ApiErrorResponse(404, HttpStatus.BAD_REQUEST,"meeting didnt find", LocalDateTime.now()));
        else if(meeting.get().getPatient()!=null)
            throw new CustomException(new ApiErrorResponse(406, HttpStatus.BAD_REQUEST,"meeting already reserved", LocalDateTime.now()));
       else {
            meeting.get().setIsDeleted(true);
            meetingRepository.updateMeeting(meeting.get());
        }
    }


    public List<MeetingReponse> findMeetingList(MeetingDto search){
        if(search.getMobile()==null)
        throw new CustomException(new ApiErrorResponse(400, HttpStatus.BAD_REQUEST,"please enter your mobile", LocalDateTime.now()));
        List<Tuple> tuples = meetingRepository.findMeetingClient(search);
        List<MeetingReponse> meetingReponses = new ArrayList<>();
        tuples.stream().forEach(i->{
           MeetingReponse meetingReponse = new MeetingReponse();
           meetingReponse.setMeetingDate((Date) i.get(0));
           meetingReponse.setEndMeetingDate((Date) i.get(1));
           meetingReponse.setPatientMobile((String) i.get(2));
           meetingReponse.setPatientName((String) i.get(3));
           meetingReponse.setDoctorMobile((String) i.get(4));
           meetingReponse.setDoctorName((String) i.get(5));
           meetingReponses.add(meetingReponse);
        });
        return meetingReponses;
    }

}
