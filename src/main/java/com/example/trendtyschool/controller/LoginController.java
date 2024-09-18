package com.example.trendtyschool.controller;

import com.example.trendtyschool.dto.LoginRequest;
import com.example.trendtyschool.dto.StatusObjectResponse;
import com.example.trendtyschool.dto.TokenResponse;
import com.example.trendtyschool.repository.UserRepository;
import com.example.trendtyschool.service.AuthService;
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

    private final AuthService authService;


    @PostMapping("/login")
    public ResponseEntity<StatusObjectResponse> login(@RequestBody LoginRequest request, HttpServletRequest httpRequest) {
        StatusObjectResponse response = userRepository.callLoginProcedure(request.getUsername(), request.getPassword(), httpRequest);
        if(response.isBoolean()){
            return ResponseEntity.ok(response);
        }else return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    @GetMapping("/logo")
    public String logo() {
        return "logo";
    }

//    @PostMapping("/login")
//    public ResponseEntity<StatusObjectResponse> login(@RequestBody LoginRequest request, HttpServletRequest httpRequest, HttpServletResponse response) {
//        TokenResponse loginResponse = authService.authenticate(request, httpRequest);
//        Cookie refreshTokenCookie = new Cookie("refreshToken", loginResponse.getRefreshToken());
//        refreshTokenCookie.setHttpOnly(true);
//        refreshTokenCookie.setSecure(true);
//        refreshTokenCookie.setMaxAge(30 * 24 * 60 * 60);
//        refreshTokenCookie.setPath("/");
//        response.addCookie(refreshTokenCookie);
//
//        return ResponseEntity.ok(
//                StatusObjectResponse.builder()
//                        .token(loginResponse)
//                        .build()
//        );
//    }
}
