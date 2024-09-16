package com.example.trendtyschool.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Course")
public class Course extends AbstractEntity{

    @Column(name = "start")
    private String start;

    @Column(name = "end")
    private String end;

    @ManyToOne
    @JoinColumn(name = "idKhoa", insertable = false, updatable = false)
    private Khoa khoa;
}
