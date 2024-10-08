package com.example.trendtyschool.dto.reponse;

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
    private Object object;
    private List<Object> listObject;

}
