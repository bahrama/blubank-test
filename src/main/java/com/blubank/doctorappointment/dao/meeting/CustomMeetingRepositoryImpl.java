package com.blubank.doctorappointment.dao.meeting;

import com.blubank.doctorappointment.dto.MeetingDto;
import com.blubank.doctorappointment.exception.CustomException;
import com.blubank.doctorappointment.model.Meeting;
import com.blubank.doctorappointment.utils.Roles;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.Calendar;
import java.util.List;

@Repository
public class CustomMeetingRepositoryImpl implements CustomMeetingRepository{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Tuple> findMeetingClient(MeetingDto search){
        String q = "select m.meeting_date startDate, m.end_meeting_date endDate\n" +
                ", um.mobile user_mobile,um.username user_name\n" +
                ", u.mobile doctor_mobile , u.username doctor_name\n" +
                "from meeting m \n" +
                (search.getRole().equals(Roles.CLIENT)?"join user_meeting um on um.id = m.patient_id\n"
                        :"full outer join user_meeting um on um.id = m.patient_id\n")+
                "join user_meeting u on u.id = m.doctor_id\n" +
                "where (m.is_deleted is null or m.is_deleted is false)\n";
        StringBuilder queryBuilder =new StringBuilder(q);
        if(search.getRole().equals(Roles.DOCTOR) && search.getUserId()!=null)
        {
            queryBuilder.append("and m.doctor_id = :v_doctorId \n");
        }
        if(search.getRole().equals(Roles.CLIENT) && search.getUserId()!=null)
        {
            queryBuilder.append("and m.patient_id = :v_patientId\n");
        }
        if(search.getRole().equals(Roles.CLIENT) && search.getMobile()!=null)
        {
            queryBuilder.append("and um.mobile = :v_mobile\n");
        }
        if(search.getRole().equals(Roles.DOCTOR) && search.getMobile()!=null)
        {
            queryBuilder.append("and u.mobile = :v_mobile\n");
        }
        if(search.getMeetingDate()!=null)
        {
            queryBuilder.append("and m.meeting_date between :v_meetingDate and :v_meetingDateNext\n");
        }
        Query query = entityManager.createNativeQuery(queryBuilder.toString(),Tuple.class);
        if(search.getRole().equals(Roles.DOCTOR) && search.getUserId()!=null)
        {
            query.setParameter("v_doctorId" , search.getUserId());
        }
        if(search.getRole().equals(Roles.CLIENT) && search.getUserId()!=null)
        {
            query.setParameter("v_patientId" , search.getUserId());
        }
        if(search.getMobile()!=null)
        {
            query.setParameter("v_mobile" , search.getMobile());
        }
        if(search.getMeetingDate()!=null)
        {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(search.getMeetingDate());
            calendar.add(Calendar.DATE, 1);
            query.setParameter("v_meetingDate" , search.getMeetingDate());
            query.setParameter("v_meetingDateNext" , calendar.getTime());
        }
        List<Tuple> meetings = query.getResultList();
        return meetings;
    }

    @Override
    public Meeting updateMeeting(Meeting meeting){
        try{
            entityManager.merge(meeting);
            return meeting;
        }catch (Exception e){
            throw new CustomException("update error");
        }
    }

}
