package com.blubank.doctorappointment.dto;

import com.blubank.doctorappointment.utils.EnumNamePattern;
import com.blubank.doctorappointment.utils.Roles;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;
@Data
@ToString
public class MeetingDto {
    private Long userId;
    @Size(max = 100)
    private String username;
    @Pattern(regexp="(^$|[0-9]{11})")
    private String mobile;
    private Long meetingId;
    @EnumNamePattern(regexp = "DOCTOR|CLIENT")
    private Roles role;
    private Date meetingDate;
    private Date endMeetingDate;
    private Boolean openClose;
    private Boolean isDeleted;
}
