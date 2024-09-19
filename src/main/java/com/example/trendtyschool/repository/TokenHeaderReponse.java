package com.example.trendtyschool.repository;

import com.example.trendtyschool.model.ClassModel.TokenAddHeaderModel;
import jakarta.servlet.http.Cookie;

public class TokenHeaderReponse {

    public static void AddTokenHeaderClient(TokenAddHeaderModel tokenAddHeaderModel){

        // Khơi tạo time expire theo ngày
        int secondsPerDay = 24 * 60 * 60; // Số giây trong một ngày

        Cookie refreshTokenCookie = new Cookie(tokenAddHeaderModel.getKey(), tokenAddHeaderModel.getValue());
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setSecure(true);
        refreshTokenCookie.setMaxAge(secondsPerDay * tokenAddHeaderModel.getExpireDay());
        refreshTokenCookie.setPath("/");

        tokenAddHeaderModel.getHttpServletResponse().addCookie(refreshTokenCookie);
    }
}
