package com.example.trendtyschool.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "School")
public class School extends AbstractEntity{

    @Column(name = "nameSchool")
    private String nameSchool;

    @Column(name = "address")
    private String address;

    @Column(name = "logo")
    private String logo;
}
