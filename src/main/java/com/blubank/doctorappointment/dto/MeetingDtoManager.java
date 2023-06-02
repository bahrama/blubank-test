package com.blubank.doctorappointment.dto;

import com.blubank.doctorappointment.model.Meeting;
import com.blubank.doctorappointment.model.User;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MeetingDtoManager {

    @Mapping(source ="user.id",target = "userId")
    @Mapping(source ="user.username",target = "username")
    @Mapping(source ="user.role",target = "role")
    @Mapping(source ="user.mobile",target = "mobile")
    @Mapping(source ="meeting.id",target = "meetingId")
    @Mapping(source ="meeting.meetingDate",target = "meetingDate")
    @Mapping(source ="meeting.openClose",target = "openClose")
    @Mapping(source ="meeting.endMeetingDate",target = "endMeetingDate")
    @Mapping(source ="meeting.isDeleted",target = "isDeleted")
    MeetingDto transferMeetingToDto(User user, Meeting meeting);


    @InheritInverseConfiguration
    Meeting transferDtoToMeetingEntity(MeetingDto meetingDto);

    @Mapping(source ="id",target = "userId")
    @Mapping(source ="username",target = "username")
    @Mapping(source ="role",target = "role")
    @Mapping(source ="password",target = "password")
    @Mapping(source ="mobile",target = "mobile")
    UserDto transferUserToDto(User user);

    @InheritInverseConfiguration
    User transferDtoToUserEntity(UserDto userDto);

}
