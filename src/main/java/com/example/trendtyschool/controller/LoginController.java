package com.example.trendtyschool.controller;

import com.example.trendtyschool.dto.LoginRequest;
import com.example.trendtyschool.dto.StatusObjectResponse;
import com.example.trendtyschool.repository.UserRepository;
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
    public ResponseEntity<StatusObjectResponse> login(@RequestBody LoginRequest request) {
        StatusObjectResponse response = userRepository.callLoginProcedure(request.getUsername(), request.getPassword());
        if(response.isBoolean()){
            return ResponseEntity.ok(response);
        }else return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }
}
