package com.example.trendtyschool.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ClassTeacher")
public class ClassTeacher extends AbstractEntity{

    @ManyToOne
    @JoinColumn(name = "idClassSchool", insertable = false, updatable = false)
    private ClassSchool classSchool;

    @ManyToOne
    @JoinColumn(name = "idAccount", insertable = false, updatable = false)
    private Account account;
}
