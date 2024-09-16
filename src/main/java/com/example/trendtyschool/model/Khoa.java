package com.example.trendtyschool.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Account")
public class Khoa extends AbstractEntity{

    @Column(name = "nameKhoa")
    private String nameKhoa;

    @Column(name = "maKhoa")
    private String maKhoa;

    @ManyToOne
    @JoinColumn(name = "idSchool", insertable = false, updatable = false)
    private School school;

    @ManyToOne
    @JoinColumn(name = "idAccount", insertable = false, updatable = false)
    private Account account;
}
