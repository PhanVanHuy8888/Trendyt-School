package com.example.trendtyschool.dto;

import lombok.*;

import java.util.List;

@Getter
@Builder
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StatusObjectResponse {
    private boolean isBoolean;
    private String errorMessage;
    private Object token;
    private List<Object> listObject;

}
