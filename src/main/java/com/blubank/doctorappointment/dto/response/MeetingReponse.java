package com.blubank.doctorappointment.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
public class MeetingReponse {

    private String patientMobile;
    private String doctorMobile;
    private String doctorName;
    private String patientName;
    private Date meetingDate;
    private Date endMeetingDate;

}
