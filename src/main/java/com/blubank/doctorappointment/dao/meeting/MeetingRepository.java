package com.blubank.doctorappointment.dao.meeting;

import com.blubank.doctorappointment.model.Meeting;
import com.blubank.doctorappointment.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MeetingRepository  extends JpaRepository<Meeting,Long> , CustomMeetingRepository{

    @Query(nativeQuery = true , value = "select * from Meeting m where m.open_close = ?1 and m.doctor_id = ?2")
    List<Meeting> findMeetingsOpenCloseAndUserId(Boolean openClose , Long userId);


}
