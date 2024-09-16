package com.example.trendtyschool.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Account")
public class Account extends AbstractEntity{

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

    @ManyToOne
    @JoinColumn(name = "idRole" )
    private Role role;

    @Column(name = "isActive")
    private boolean isActive;

    @ManyToOne
    @JoinColumn(name = "idSchool", insertable = false, updatable = false)
    private School school;

}
