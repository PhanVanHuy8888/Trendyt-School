package com.example.trendtyschool.controller;

import com.example.trendtyschool.dto.LoginRequest;
import com.example.trendtyschool.dto.StatusObjectResponse;
import com.example.trendtyschool.dto.TokenResponse;
import com.example.trendtyschool.repository.UserRepository;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class LoginController {

    private final UserRepository userRepository;




    @PostMapping("/login")
    public ResponseEntity<StatusObjectResponse> login(@RequestBody LoginRequest request, HttpServletRequest httpRequest, HttpServletResponse response) {
        StatusObjectResponse loginResponse = userRepository.callLoginProcedure(request.getUsername(), request.getPassword(), httpRequest);

        if (loginResponse.isBoolean()) {
            Object responseObject = loginResponse.getToken();
            if (responseObject instanceof TokenResponse) {
                TokenResponse tokenResponse = (TokenResponse) responseObject;
                Cookie refreshTokenCookie = new Cookie("refreshToken", tokenResponse.getRefreshToken());
                refreshTokenCookie.setHttpOnly(true);
                refreshTokenCookie.setSecure(true);
                refreshTokenCookie.setMaxAge(30 * 24 * 60 * 60); // 30 ngày
                refreshTokenCookie.setPath("/");
                response.addCookie(refreshTokenCookie);
                return ResponseEntity.ok(loginResponse);
            } else {
                // Xử lý trường hợp object không phải là TokenResponse (nếu có)
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(loginResponse);
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(loginResponse);
        }
    }

    @GetMapping("/logo")
    public String logo() {
        return "logo";
    }


}
