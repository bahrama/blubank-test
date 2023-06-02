package com.blubank.doctorappointment.dto;

import com.blubank.doctorappointment.utils.Roles;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import java.util.Date;
@Getter
@Setter
public class MeetingDto {
    private Long userId;
    private String username;
    private String mobile;
    private Long meetingId;
    private Roles role;
    private Date meetingDate;
    private Date endMeetingDate;
    private Boolean openClose;
    private Boolean isDeleted;
}
