package com.example.trendtyschool.model.ClassModel;

import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TokenAddHeaderModel {
    String key;
    String value;
    int expireDay;
    HttpServletResponse httpServletResponse;
}
