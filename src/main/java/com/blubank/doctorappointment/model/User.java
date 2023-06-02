package com.blubank.doctorappointment.model;

import com.blubank.doctorappointment.utils.Roles;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Slf4j
@Entity
@Table(name="user_meeting" , schema = "public")
@NoArgsConstructor
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="ID")
    private Long id;
    @Column(name = "username" , length = 100 , unique = true , nullable = false)
    private String username;
    @Column(name = "mobile" , length = 11, unique = true , nullable = false)
    private String mobile;
    @Column(name = "PASSWORD" , length = 200 , nullable = false)
    private String password;
    @Column(name = "role" , length = 50)
    @Enumerated(EnumType.STRING)
    private Roles role;

    @OneToMany(fetch=FetchType.LAZY,
            mappedBy="doctor",
            cascade= CascadeType.ALL)
    private Set<Meeting> meetings = new HashSet<>();

    @OneToMany(fetch=FetchType.LAZY,
            mappedBy="patient",
            cascade= CascadeType.ALL)
    private Set<Meeting> meetings2 = new HashSet<>();

}
