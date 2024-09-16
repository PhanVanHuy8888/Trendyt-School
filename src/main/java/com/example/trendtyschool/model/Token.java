package com.example.trendtyschool.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Token")
public class Token extends AbstractEntity{

    @Column(name = "accessToken")
    private String accessToken;

    @Column(name = "refeshToken")
    private String refeshToken;

    private Integer idAccount;

    @Column(name = "expriseAccess", nullable = true)
    private Date expriseAccess;

    @Column(name = "ipAddress")
    private String ipAddress;

    @Column(name = "hostName")
    private String hostName;

    @Column(name = "browserName")
    private String browserName;

    @Column(name = "course")
    private String course;
}
