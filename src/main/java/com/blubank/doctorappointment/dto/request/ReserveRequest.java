package com.blubank.doctorappointment.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ReserveRequest {
   private Long meetingId;
   private String patientMobile;
   private Boolean isDeleted;
}
