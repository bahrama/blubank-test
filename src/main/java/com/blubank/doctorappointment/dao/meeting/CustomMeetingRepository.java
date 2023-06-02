package com.blubank.doctorappointment.dao.meeting;

import com.blubank.doctorappointment.dto.MeetingDto;
import com.blubank.doctorappointment.model.Meeting;

import javax.persistence.Tuple;
import java.util.List;

public interface CustomMeetingRepository {
    List<Tuple> findMeetingClient(MeetingDto search);

    Meeting updateMeeting(Meeting meeting);
}
