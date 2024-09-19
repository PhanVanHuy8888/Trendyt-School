package com.example.trendtyschool.model.Entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Role")
public class Role extends AbstractEntity {

    @Column(name = "nameRole", nullable = false)
    private String nameRole;

    @Column(name = "exp")
    private int exp;

    @Column(name = "lv")
    private int lv;

    @Column(name = "idParent")
    private Integer idParent;


}
