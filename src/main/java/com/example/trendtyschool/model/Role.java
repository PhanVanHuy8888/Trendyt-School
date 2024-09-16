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
@Table(name = "Role")
public class Role extends AbstractEntity{

    @Column(name = "nameRole", nullable = false)
    private String nameRole;

    @Column(name = "exp")
    private int exp;

    @Column(name = "lv")
    private int lv;

    @Column(name = "idParent")
    private Integer idParent;


}
