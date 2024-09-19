package com.example.trendtyschool.model.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "AccountStudent2021")
public class AccountStudent2021 extends AbstractEntity {

    @Column(name = "userName", nullable = false, unique = true)
    private String userName;

    @Column(name = "passWord", nullable = false)
    private String passWord;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "fullName")
    private String fullName;

    @Column(name = "address")
    private String address;

    @Column(name = "sex" )
    private boolean sex;

    @Column(name = "birthDay")
    private Date birthDay;

    @Column(name = "imgAccount", columnDefinition = "TEXT" )
    private String imgAccount;

    @Column(name = "isActive")
    private boolean isActive;

    @ManyToOne
    @JoinColumn(name = "idRole" )
    private Role role;

    @ManyToOne
    @JoinColumn(name = "idSchool", insertable = false, updatable = false)
    private School school;

    @ManyToOne
    @JoinColumn(name = "idCourse", insertable = false, updatable = false)
    private Course course;

    @ManyToOne
    @JoinColumn(name = "idClassSchool", insertable = false, updatable = false)
    private ClassSchool classSchool;

}
