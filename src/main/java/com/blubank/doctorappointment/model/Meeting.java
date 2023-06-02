package com.blubank.doctorappointment.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Slf4j
@Entity
@Table(name="meeting" , schema = "public" , uniqueConstraints =@UniqueConstraint(columnNames= {"meeting_date" , "end_meeting_date" , "doctor_id"}))
@NoArgsConstructor
@Getter
@Setter
public class Meeting {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="id")
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "meeting_date")
    private Date meetingDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "end_meeting_date")
    private Date endMeetingDate;

    @Column(name = "open_close")
    private Boolean openClose;

    @Column(name = "is_deleted")
    private Boolean isDeleted;


    @ManyToOne(cascade= CascadeType.ALL , fetch = FetchType.LAZY)
    @JoinColumn(name="doctor_id")
    private User doctor;

    @ManyToOne(cascade= CascadeType.ALL , fetch = FetchType.LAZY)
    @JoinColumn(name="patient_id")
    private User patient;

}
