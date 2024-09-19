package com.example.trendtyschool.model.Entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Account")
public class ClassSchool extends AbstractEntity {

    @Column(name = "nameClass")
    private String nameClass;

    @ManyToOne
    @JoinColumn(name = "idCourse", insertable = false, updatable = false)
    private Course course;
}
